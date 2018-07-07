package com.autokartz.autokartz.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.interfaces.ForgotPasswordListsner;
import com.autokartz.autokartz.services.databases.preferences.AccountDetailHolder;
import com.autokartz.autokartz.services.webServices.apiRequests.ForgotPasswordApi;
import com.autokartz.autokartz.utils.apiResponses.ForgotPasswordBeanResponse;
import com.autokartz.autokartz.utils.util.AppToast;
import com.autokartz.autokartz.utils.util.InputValidation;
import com.autokartz.autokartz.utils.util.dialogs.DismissDialog;
import com.autokartz.autokartz.utils.util.dialogs.ShowDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordActivity extends AppCompatActivity implements ForgotPasswordListsner {

    @BindView(R.id.enter_email_et)
    EditText mEmail;
    @BindView(R.id.enter_phone_et)
    EditText mPhone;
    @BindView(R.id.forgot_password_btn)
    Button mSubmit;
    String email;
    String phone;

    private static final String TAG = ForgotPasswordActivity.class.getName();
    private Context mContext;
    private AccountDetailHolder mAccountDetailHolder;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mContext = getApplicationContext();
    }

    private boolean isInputValid() {
        if (InputValidation.validateEmail(mEmail) && InputValidation.validateMobile(mPhone)) {
            return true;
        }
        return false;
    }

    @OnClick({R.id.forgot_password_btn})
    public void SignIn() {
        if (isInputValid()) {
            email = mEmail.getText().toString().trim();
            phone = mPhone.getText().toString().trim();
            mProgressDialog = ShowDialog.show(this, "", "Sending OTP..", true, false);
            ForgotPasswordApi forgotPasswordApi = new ForgotPasswordApi(mContext, this, mProgressDialog);
            forgotPasswordApi.callForgotPasswordApi(email, phone);
        } else {
            Toast.makeText(mContext, "Please enter valid input details.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void forgotPasswordListsner(boolean success, ForgotPasswordBeanResponse forgotPasswordBeanResponse) {
        if (success) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            String otp = forgotPasswordBeanResponse.getOtp();
            String user_id = forgotPasswordBeanResponse.getUserId();
            Intent intent = new Intent(this, OtpActivity.class);
            intent.putExtra("email", email);
            intent.putExtra("phone", phone);
            intent.putExtra("otp", otp);
            intent.putExtra("user_id", user_id);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            DismissDialog.dismissWithCheck(mProgressDialog);
            AppToast.showToast(mContext, "please enter valid details ");

        }
    }
}

