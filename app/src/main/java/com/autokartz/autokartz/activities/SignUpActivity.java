package com.autokartz.autokartz.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.interfaces.SignUpResponseListsner;
import com.autokartz.autokartz.services.databases.preferences.AccountDetailHolder;
import com.autokartz.autokartz.services.webServices.apiRequests.ManualSignUpApi;
import com.autokartz.autokartz.utils.apiRequestBeans.SignUpBean;
import com.autokartz.autokartz.utils.apiResponses.UserDetailBean;
import com.autokartz.autokartz.utils.util.InputValidation;
import com.autokartz.autokartz.utils.util.dialogs.DismissDialog;
import com.autokartz.autokartz.utils.util.dialogs.ShowDialog;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SignUpActivity extends AppCompatActivity implements SignUpResponseListsner {

    @BindView(R.id.et_garrage_owner_name)
    EditText mGarrageOwnerName;
    @BindView(R.id.et_customer_name)
    EditText mCustomerName;
    @BindView(R.id.et_email)
    EditText mEmail;
    @BindView(R.id.et_phonenumeber)
    EditText mPhoneNumber;
    @BindView(R.id.et_password)
    EditText mPassword;
    @BindView(R.id.et_repassword)
    EditText mRePassword;
    @BindView(R.id.btn_signup)
    Button mSignup;
    @BindView(R.id.tv_loginhere)
    TextView mLogin;
    @BindView(R.id.et_address_name)
    EditText mAddress;
    @BindView(R.id.et_city_name)
    EditText mCity;
    @BindView(R.id.et_pin_code)
    EditText mPinCode;
    @BindView(R.id.et_state_name)
    EditText mState;
    private static final String LOG_TAG = SignUpActivity.class.getName();
    private FirebaseAuth mAuth;
    private Context mContext;
    private AccountDetailHolder mAccountDetailHolder;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initVariables();
    }

    private void initVariables() {
        mContext = getApplicationContext();
        mAccountDetailHolder = new AccountDetailHolder(mContext);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                SignUpActivity.this);
        alertDialog2.setMessage("Are you sure you want to cancel signup");
        alertDialog2.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
        alertDialog2.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        alertDialog2.show();
        return;
    }
    private boolean isInputValid() {
        if (InputValidation.validateFirstName(mGarrageOwnerName) && InputValidation.validateLastName(mCustomerName)
                && InputValidation.validateAddress(mAddress) && InputValidation.validateCity(mCity)
                && InputValidation.validateState(mState) && InputValidation.validatePin(mPinCode)
                && InputValidation.validateEmail(mEmail) && InputValidation.validateMobile(mPhoneNumber)
                && InputValidation.validatePassword(mPassword) && InputValidation.validatePassword(mRePassword)) {
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

    @OnClick({R.id.btn_signup})
    public void SignIn() {
        if (isInputValid()) {
            String garrageOwnerName = mGarrageOwnerName.getText().toString().trim();
            String customerName = mCustomerName.getText().toString().trim();
            String email = mEmail.getText().toString().trim();
            String phonenumber = mPhoneNumber.getText().toString().trim();
            String password = mPassword.getText().toString().trim();
            String address = mAddress.getText().toString().trim();
            String city = mCity.getText().toString().trim();
            String state = mState.getText().toString().trim();
            String pincode = mPinCode.getText().toString().trim();
            SignUpBean signUpBean = new SignUpBean(garrageOwnerName, customerName, address, city, state, pincode, email, password, phonenumber);
            mProgressDialog = ShowDialog.show(this, "", "Please Wait", true, false);
            ManualSignUpApi manualSignUpApi = new ManualSignUpApi(mContext, this, mProgressDialog);
            manualSignUpApi.callSignUpApi(signUpBean);
        } else {
            Toast.makeText(mContext, "Please enter valid input details.", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.tv_loginhere})
    public void onClickLoginTv() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void signUpResponse(boolean status, UserDetailBean userDetailBean) {
        if (status) {
            mAccountDetailHolder.setUserDetailBean(userDetailBean);
            mAccountDetailHolder.setUserLoggedIn(true);
            DismissDialog.dismissWithCheck(mProgressDialog);
            Intent mainDashboardIntent = new Intent(this, MainDashboardActivity.class);
            mainDashboardIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mainDashboardIntent);
        } else {
            mAccountDetailHolder.setUserLoggedIn(false);
            DismissDialog.dismissWithCheck(mProgressDialog);
        }
    }
}