package com.autokartz.autokartz.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.interfaces.LoginResponseListener;
import com.autokartz.autokartz.services.databases.preferences.AccountDetailHolder;
import com.autokartz.autokartz.services.webServices.apiRequests.ManualLoginApi;
import com.autokartz.autokartz.utils.apiResponses.UserDetailBean;
import com.autokartz.autokartz.utils.util.Logger;
import com.autokartz.autokartz.utils.util.dialogs.DismissDialog;
import com.autokartz.autokartz.utils.util.dialogs.ShowDialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginResponseListener {

    @BindView(R.id.login_button)
    Button mLoginButton;
    @BindView(R.id.et_emaillogin)
    EditText mEmail;
    @BindView(R.id.et_passwordlogin)
    EditText mPassword;
    private static final String TAG = LoginActivity.class.getName();
    private Context mContext;
    private AccountDetailHolder mAccountDetailHolder;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    private void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Logger.LogDebug("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        initVariables();
        printKeyHash();
    }

    private void initVariables() {
        mContext = getApplicationContext();
        mAccountDetailHolder = new AccountDetailHolder(mContext);
    }

    @OnClick({R.id.register_button})
    public void onClickSignUpTv() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @OnClick({R.id.forgot_password_tv})
    public void onClickForgotPasswordTv() {
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @OnClick({R.id.login_button})
    public void startLogin() {
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        if (email == null || email.isEmpty()) {
            mEmail.setError("Enter Email");
        }
        if (password == null || password.isEmpty()) {
            mPassword.setError("Enter Password");
        }
        if (!email.isEmpty() && !password.isEmpty()) {
            mProgressDialog = ShowDialog.show(this, "", "Please Wait", true, false);
            ManualLoginApi manualLoginApi = new ManualLoginApi(mContext, this, mProgressDialog);
            manualLoginApi.callLoginApi(email, password);
        }
    }

    @Override
    public void getLoginResponse(boolean status, UserDetailBean userDetailBean) {
        if (status) {
            mAccountDetailHolder.setUserDetailBean(userDetailBean);
            mAccountDetailHolder.setUserLoggedIn(true);
            DismissDialog.dismissWithCheck(mProgressDialog);
            Intent mainDashBoardIntent = new Intent(this, MainDashboardActivity.class);
            mainDashBoardIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mainDashBoardIntent);
        } else {
            mAccountDetailHolder.setUserLoggedIn(false);
            DismissDialog.dismissWithCheck(mProgressDialog);
        }
    }
}