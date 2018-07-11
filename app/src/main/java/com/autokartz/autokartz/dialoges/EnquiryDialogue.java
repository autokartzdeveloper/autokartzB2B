package com.autokartz.autokartz.dialoges;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.utils.pojoClasses.CarInformation;
import com.autokartz.autokartz.utils.util.InputValidation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class EnquiryDialogue extends Dialog {


    private Activity mActivity;
    private Context mContext;
    private CarInformation mCarInfo;
    @BindView(R.id.et_customer_name)
    EditText mCustomerName;
    @BindView(R.id.et_email)
    EditText mEmail;
    @BindView(R.id.et_phonenumeber)
    EditText mPhoneNumber;
    @BindView(R.id.et_address_name)
    EditText mAddress;
    @BindView(R.id.et_city_name)
    EditText mCity;
    @BindView(R.id.et_pin_code)
    EditText mPinCode;
    @BindView(R.id.et_state_name)
    EditText mState;
    @BindView(R.id.btn_signup)
    Button mSignup;

    @BindView(R.id.et_country)
    EditText mCountry;


    public EnquiryDialogue(@NonNull Context context, Activity activity, CarInformation carInfo) {
        super(activity);
        mActivity = activity;
        mContext = context;
        mCarInfo = carInfo;
        init();

    }

    private void init() {
        initVariables();
    }

    private void initVariables() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooserdetail_popup_layout);
        ButterKnife.bind(this);
        setCancelable(false);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        mActivity.getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        int height = (int) (displayMetrics.heightPixels * 0.75);
        int width = (int) (displayMetrics.widthPixels * 0.9);
        getWindow().setLayout(width, height);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }

    private boolean isInputValid() {
        return InputValidation.validateCustomerName(mCustomerName) && InputValidation.validateAddress(mAddress) && InputValidation.validateCity(mCity)
                && InputValidation.validateState(mState) && InputValidation.validateCountry(mCountry) && InputValidation.validatePin(mPinCode)
                && InputValidation.validateEmail(mEmail) && InputValidation.validateMobile(mPhoneNumber);
    }

    @OnClick({R.id.btn_signup})
    public void SignIn() {
        if (isInputValid()) {
            String customerName = mCustomerName.getText().toString().trim();
            String email = mEmail.getText().toString().trim();
            String phonenumber = mPhoneNumber.getText().toString().trim();
            String address = mAddress.getText().toString().trim();
            String city = mCity.getText().toString().trim();
            String state = mState.getText().toString().trim();
            String pincode = mPinCode.getText().toString().trim();
            String country = mCountry.getText().toString().trim();
            mCarInfo.setmCustomerName(customerName);
            mCarInfo.setmCustomerAddress(address);
            mCarInfo.setmCity(city);
            mCarInfo.setmState(state);
            mCarInfo.setmPhone(phonenumber);
            mCarInfo.setmEmail(email);
            mCarInfo.setmPin(pincode);
            mCarInfo.setmCountry(country);
            dismiss();


        } else {
            Toast.makeText(mContext, "Please enter valid input details.", Toast.LENGTH_SHORT).show();
        }
    }

}