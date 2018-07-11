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
import com.autokartz.autokartz.interfaces.ResetPasswordListsner;
import com.autokartz.autokartz.services.webServices.apiRequests.ResetPasswordApi;
import com.autokartz.autokartz.utils.util.InputValidation;
import com.autokartz.autokartz.utils.util.dialogs.ShowDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetPasswordActivity extends AppCompatActivity implements ResetPasswordListsner {
    Context mContext;
    String user_id;
    @BindView(R.id.enter_newpassword_et)
    EditText mPassword;
    @BindView(R.id.enter_new_repassword_et)
    EditText mRePassword;
    @BindView(R.id.submit_btn)
    Button mSubmit;
    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        getIntentData();
        init();
    }

    private void getIntentData() {
        user_id = getIntent().getStringExtra("user_id");
    }

    private void init() {
        initVariables();

    }

    private void initVariables() {
        mContext = getApplicationContext();
    }

    private boolean isInputValid() {
        if (InputValidation.validatePassword(mPassword)) {
            String pass = mPassword.getText().toString();
            String rePass = mRePassword.getText().toString();
            if (!pass.equalsIgnoreCase(rePass)) {
                mRePassword.setError("Password and RePassword must be same");
            } else {
                return true;
            }
        }
        return false;
    }

    @OnClick({R.id.submit_btn})
    public void ResetPassword() {
        if (isInputValid()) {
            String password = mPassword.getText().toString().trim();
            mProgressDialog = ShowDialog.show(this, "", "Please Wait", true, false);
            ResetPasswordApi resetPasswordApi = new ResetPasswordApi(mContext, this, mProgressDialog);
            resetPasswordApi.callResetPasswordApi(user_id, password);
        } else {
            Toast.makeText(mContext, "Please enter valid input details.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void resetPassword(boolean success) {
        if (success) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
