package com.autokartz.autokartz.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.interfaces.FcmTokenResponseListsner;
import com.autokartz.autokartz.services.databases.preferences.AccountDetailHolder;
import com.autokartz.autokartz.services.webServices.apiRequests.BankInfoApi;
import com.autokartz.autokartz.services.webServices.apiRequests.FcmTokenApi;
import com.autokartz.autokartz.services.webServices.apiRequests.ManualSignUpApi;
import com.autokartz.autokartz.utils.apiRequestBeans.SignUpBean;
import com.autokartz.autokartz.utils.apiResponses.UserDetailBean;
import com.autokartz.autokartz.utils.util.InputValidation;
import com.autokartz.autokartz.utils.util.dialogs.DismissDialog;
import com.autokartz.autokartz.utils.util.dialogs.ShowDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Cortana on 1/3/2018.
 */

public class BankInfoFragment extends Fragment implements FcmTokenResponseListsner {
    private UserDetailBean mUserDetailBean;
    private AccountDetailHolder mAccountDetailHolder;
    private Context mContext;
    @BindView(R.id.et_bank_account)
    EditText mbankAccountEt;
    @BindView(R.id.et_ifsc)
    EditText mbankIfscEt;
    @BindView(R.id.et_branch)
    EditText mBranchEt;
    @BindView(R.id.et_pan)
    EditText mPanNumEt;
    @BindView(R.id.et_gst_number)
    EditText mGstEt;
    ProgressDialog mProgressDialog;
    String bank_account;
    String bank_ifsc;
    String pan_number;
    String bank_branch;
    String gst_number;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bank_info, container, false);
    return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Bank Information");
        }

    }


    private void init() {
        initVariables();
    }

    private void initVariables() {
        mContext = getContext();
        mAccountDetailHolder = new AccountDetailHolder(mContext);
        mUserDetailBean = mAccountDetailHolder.getUserDetailBean();
        setViews();
        getViews();
    }

    private void setViews() {
        mAccountDetailHolder.getUserDetailBean();
        bank_account = mAccountDetailHolder.getUserBankDetailBean().getAccountNo();
        bank_ifsc = mAccountDetailHolder.getUserBankDetailBean().getIfsc();
        bank_branch = mAccountDetailHolder.getUserBankDetailBean().getBranch();
        pan_number = mAccountDetailHolder.getUserBankDetailBean().getPan();
        gst_number = mAccountDetailHolder.getUserBankDetailBean().getGst();
        mbankAccountEt.setText(bank_account);
        mbankIfscEt.setText(bank_ifsc);
        mBranchEt.setText(bank_branch);
        mPanNumEt.setText(pan_number);
        mGstEt.setText(gst_number);
    }

    private void getViews() {

    }

    private boolean isInputValid() {
        if (InputValidation.validateBankAcName(mbankAccountEt) && InputValidation.validateIfscCode(mbankIfscEt)
                && InputValidation.validateBranch(mBranchEt) && InputValidation.validatePanNumber(mPanNumEt)
                && InputValidation.validateGstNumber(mGstEt)) {
            return true;
        }
        return false;
    }


    @OnClick({R.id.btn_account_add_submit})

    public void submit() {
        if (isInputValid()) {
            String user_id = mUserDetailBean.getUserId();
            String account_no = mbankAccountEt.getText().toString().trim();
            String ifsc = mbankIfscEt.getText().toString().trim();
            String branch = mBranchEt.getText().toString().trim();
            String pan = mPanNumEt.getText().toString().trim();
            String gst = mGstEt.getText().toString().trim();
            mProgressDialog = ShowDialog.show(mContext, "", "Please Wait", true, false);
            BankInfoApi bankinfoApi = new BankInfoApi(mContext, this, mProgressDialog);
            bankinfoApi.postBankInfo(user_id, account_no, ifsc, branch, pan, gst);
        } else {
            Toast.makeText(mContext, "Please enter valid Account details.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void fcmTokenResponse(boolean success) {
        if (success) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            MyAccountFragment fragment = new MyAccountFragment();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_frame, fragment);
            fragmentTransaction.commit();

        }
    }
}