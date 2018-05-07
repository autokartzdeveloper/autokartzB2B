package com.autokartz.autokartz.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.interfaces.BankInfoResponseListener;
import com.autokartz.autokartz.services.databases.preferences.AccountDetailHolder;
import com.autokartz.autokartz.services.webServices.apiRequests.GetBankInfoAPI;
import com.autokartz.autokartz.services.webServices.apiRequests.GetOrdersAPI;
import com.autokartz.autokartz.utils.apiResponses.UserBankInfo;
import com.autokartz.autokartz.utils.apiResponses.UserDetailBean;
import com.autokartz.autokartz.utils.pojoClasses.CarInformation;
import com.autokartz.autokartz.utils.pojoClasses.CategoryInformation;
import com.autokartz.autokartz.utils.util.constants.IntentKeyConstants;
import com.autokartz.autokartz.utils.util.dialogs.DismissDialog;
import com.autokartz.autokartz.utils.util.dialogs.ShowDialog;
import com.facebook.internal.LockOnGetVariable;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Cortana on 1/3/2018.
 */

public class MyAccountFragment extends Fragment implements BankInfoResponseListener {
    private UserDetailBean mUserDetailBean;
    private AccountDetailHolder mAccountDetailHolder;
    private Context mContext;
    @BindView(R.id.account_char_name_tv)
    TextView mFirstLetterNameTv;
    @BindView(R.id.account_garage_owner_nameval_tv)
    TextView mGarageNameTv;
    @BindView(R.id.account_emailval_tv)
    TextView mEmailTv;
    @BindView(R.id.account_phoneval_tv)
    TextView mPhoneTv;
    @BindView(R.id.addressval_tv)
    TextView mAddressTv;
    @BindView(R.id.bank_ac_val_tv)
    TextView mBankAccount;
    @BindView(R.id.ifsc_val_tv)
    TextView mIfscCode;
    @BindView(R.id.bank_branch_val_tv)
    TextView mBankBranch;
    @BindView(R.id.pan_val_tv)
    TextView mPan;
    @BindView(R.id.gst_val_tv)
    TextView mGst;
    @BindView(R.id.linear_bank_details_layout)
    LinearLayout mLinearBankDetailsLayout;
    private static final String CURRENT_TAG = MyAccountFragment.class.getName();
    ProgressDialog mProgressDialog;
    String account_no;
    String branch;
    String ifsc;
    String pan;
    String gst;
    private MenuItem addBankInfo;
    private MenuItem updateBankInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myaccount, container, false);
        setHasOptionsMenu(true);

        getActivity().invalidateOptionsMenu();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("MY ACCOUNT");

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.account_bank_details, menu);
        addBankInfo = menu.findItem(R.id.item_add_bank);
        updateBankInfo = menu.findItem(R.id.item_update_bank);
        updateBankInfo.setVisible(false);
        //menu.findItem(R.id.update_bank).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_add_bank:
                // TODO put your code here to respond to the button tap
                addBankInformation();
                return true;
            case R.id.item_update_bank:
                // TODO put your code here to respond to the button tap
                //  Toast.makeText(getActivity(), "update!", Toast.LENGTH_SHORT).show();
                updateBankInformation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateBankInformation() {
        BankInfoFragment fragment = new BankInfoFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment, CURRENT_TAG);
        fragmentTransaction.addToBackStack(CURRENT_TAG);
        fragmentTransaction.commit();
    }

    private void addBankInformation() {
        BankInfoFragment fragment = new BankInfoFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment, CURRENT_TAG);
        fragmentTransaction.addToBackStack(CURRENT_TAG);
        fragmentTransaction.commit();
    }

    private void init() {
        initVariables();
    }

    private void initVariables() {
        mContext = getContext();
        mAccountDetailHolder = new AccountDetailHolder(mContext);
        mUserDetailBean = mAccountDetailHolder.getUserDetailBean();
        getUserBankInfo();
        getViews();
    }

    private void getUserBankInfo() {
        String userId = mAccountDetailHolder.getUserDetailBean().getUserId();
        mProgressDialog = ShowDialog.show(mContext, "", "Please Wait", true, false);
        GetBankInfoAPI getBankInfoAPI = new GetBankInfoAPI(mContext, this, mProgressDialog);
        getBankInfoAPI.getBankDetailApi(userId);
    }

    private void getViews() {
        String garage_owner_name = mUserDetailBean.getGarageOwnerName();
        String email_id = mUserDetailBean.getEmailId();
        String phone = mUserDetailBean.getMobile();
        String address = mUserDetailBean.getAddress();
        char firstlettername = garage_owner_name.charAt(0);
        mGarageNameTv.setText(garage_owner_name);
        mEmailTv.setText(email_id);
        mPhoneTv.setText(phone);
        mAddressTv.setText(address);
        mFirstLetterNameTv.setText(String.valueOf(firstlettername));

    }


    @Override
    public void getBankInfoResponse(boolean success, UserBankInfo userBankInfo) {
        if (success) {
            mLinearBankDetailsLayout.setVisibility(View.VISIBLE);
            DismissDialog.dismissWithCheck(mProgressDialog);
            account_no = userBankInfo.getAccountNo();
            branch = userBankInfo.getBranch();
            ifsc = userBankInfo.getIfsc();
            pan = userBankInfo.getPan();
            gst = userBankInfo.getGst();
            if (!account_no.isEmpty() && account_no != null && !branch.isEmpty() && branch != null &&
                    !ifsc.isEmpty() && ifsc != null && !pan.isEmpty() && pan != null && !gst.isEmpty() && gst != null) {
                mBankAccount.setText(account_no);
                mBankBranch.setText(branch);
                mIfscCode.setText(ifsc);
                mPan.setText(pan);
                mGst.setText(gst);
                updateBankInfo.setVisible(true);
                addBankInfo.setVisible(false);
                mAccountDetailHolder.setUserBankDetailBean(userBankInfo);
            }
        } else {
            DismissDialog.dismissWithCheck(mProgressDialog);
            mAccountDetailHolder.setUserBankDetailBean(userBankInfo);
            mLinearBankDetailsLayout.setVisibility(View.GONE);
        }
    }
}