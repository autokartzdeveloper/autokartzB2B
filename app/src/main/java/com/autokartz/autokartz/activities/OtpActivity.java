package com.autokartz.autokartz.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.interfaces.ForgotPasswordListsner;
import com.autokartz.autokartz.services.webServices.apiRequests.ForgotPasswordApi;
import com.autokartz.autokartz.utils.apiResponses.ForgotPasswordBeanResponse;
import com.autokartz.autokartz.utils.util.AppToast;
import com.autokartz.autokartz.utils.util.dialogs.DismissDialog;
import com.autokartz.autokartz.utils.util.dialogs.ShowDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OtpActivity extends AppCompatActivity implements ForgotPasswordListsner {
    Context mContext;
    @BindView(R.id.enter_code_otp_et)
    EditText mOTP;
    String otp;
    String email;
    String phone;
    String user_id;
    ProgressDialog mProgressDialog;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        ButterKnife.bind(this);
        init();
        getIntentData();

    }

    private void init() {
        mContext = getApplicationContext();
    }

    private void getIntentData() {

        otp = getIntent().getStringExtra("otp");
        Log.v("qwer", otp);
        user_id = getIntent().getStringExtra("user_id");
        email = getIntent().getStringExtra("email");
        phone = getIntent().getStringExtra("phone");

    }

    @OnClick({R.id.resend_otp})
    public void ResendOtp() {
        mProgressDialog = ShowDialog.show(this, "", "Sending OTP..", true, false);
        ForgotPasswordApi forgotPasswordApi = new ForgotPasswordApi(mContext, this, mProgressDialog);
        forgotPasswordApi.callForgotPasswordApi(email, phone);
    }


    @OnClick({R.id.submit_otp_btn})
    public void Submit() {
        String otptext = mOTP.getText().toString().trim();
        if (otptext.matches(otp)) {
            Intent intent = new Intent(this, ResetPasswordActivity.class);
            mProgressDialog = ShowDialog.show(this, "", "confirming OTP...", true, false);
            intent.putExtra("user_id", user_id);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(mContext, "Please enter valid OTP", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void forgotPasswordListsner(boolean success, ForgotPasswordBeanResponse forgotPasswordBeanResponse) {
        if (success) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            String otp = forgotPasswordBeanResponse.getOtp();
            String user_id = forgotPasswordBeanResponse.getUserId();
            Intent intent = new Intent(this, OtpActivity.class);
            intent.putExtra("otp", otp);
            intent.putExtra("user_id", user_id);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            DismissDialog.dismissWithCheck(mProgressDialog);


        }
    }

}
