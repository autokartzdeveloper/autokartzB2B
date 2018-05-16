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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.autokartz.autokartz.R;
import com.autokartz.autokartz.interfaces.BankInfoResponseListener;
import com.autokartz.autokartz.services.databases.preferences.AccountDetailHolder;
import com.autokartz.autokartz.services.webServices.apiRequests.GetBankInfoAPI;
import com.autokartz.autokartz.utils.apiResponses.UserBankInfo;
import com.autokartz.autokartz.utils.apiResponses.UserDetailBean;
import com.autokartz.autokartz.utils.util.dialogs.DismissDialog;
import com.autokartz.autokartz.utils.util.dialogs.ShowDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cortana on 1/3/2018.
 */

public class AutoCashFragment extends Fragment {
    private UserDetailBean mUserDetailBean;
    private AccountDetailHolder mAccountDetailHolder;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_autocash, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("AutoCash");
    }

    private void init() {
        initVariables();
    }

    private void initVariables() {
        mContext = getContext();
        mAccountDetailHolder = new AccountDetailHolder(mContext);
        mUserDetailBean = mAccountDetailHolder.getUserDetailBean();
        getViews();
    }

    private void getViews() {

    }


}