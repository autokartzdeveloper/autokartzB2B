package com.autokartz.autokartz.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.adapters.EnquiryFormsAdapter;
import com.autokartz.autokartz.interfaces.EnquiryFormsAdapterListener;
import com.autokartz.autokartz.interfaces.GetEnquiryFormsResponseListener;
import com.autokartz.autokartz.services.databases.preferences.AccountDetailHolder;
import com.autokartz.autokartz.services.webServices.apiRequests.GetEnquiryFormsApi;
import com.autokartz.autokartz.utils.apiResponses.EnquiryFormsResponseBean;
import com.autokartz.autokartz.utils.util.constants.IntentKeyConstants;
import com.autokartz.autokartz.utils.util.dialogs.DismissDialog;
import com.autokartz.autokartz.utils.util.dialogs.ShowDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 2/26/2018.
 */

public class EnquiryFormsFragment extends Fragment implements GetEnquiryFormsResponseListener, EnquiryFormsAdapterListener {

    @BindView(R.id.enquiry_forms_rv)
    RecyclerView mEnquiryFormsRv;
    @BindView(R.id.fab_add_enquiry_form)
    FloatingActionButton mAddEnuiryFormFabBtn;
    @BindView(R.id.no_enquiry_form_tv)

    TextView mNoEnquiryFormTv;

    @BindView(R.id.linearlayout1)
    LinearLayout mLinearLayout;

    private Context mContext;
    private Activity mActivity;
    private ArrayList<Integer> enquiryFormList;
    private EnquiryFormsAdapter mEnquiryFormsAdapter;
    private ProgressDialog mProgressDialog;
    private AccountDetailHolder mAccountDetailHolder;
    private static final String CURRENT_TAG = EnquiryFormsFragment.class.getName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enquiry_forms, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("Enquiry Forms");

        init();
    }

    private void init() {
        mLinearLayout.setVisibility(View.GONE);
        initVariables();
        mProgressDialog = ShowDialog.show(mContext, "", "Please Wait", true, false);
        callGetEnquiryFormsApi();
    }

    private void callGetEnquiryFormsApi() {
        String userId = mAccountDetailHolder.getUserDetailBean().getUserId();
        GetEnquiryFormsApi getEnquiryFormsApi = new GetEnquiryFormsApi(mContext, mProgressDialog, this);
        getEnquiryFormsApi.callEnquiryFormApi(userId);
    }

    private void initVariables() {
        mContext = getContext();
        mActivity = getActivity();
        mAccountDetailHolder = new AccountDetailHolder(mContext);
        setRvAdapter();
    }

    private void setRvAdapter() {
        mEnquiryFormsAdapter = new EnquiryFormsAdapter(mContext, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mEnquiryFormsRv.setLayoutManager(layoutManager);
        mEnquiryFormsRv.setItemAnimator(new DefaultItemAnimator());
        mEnquiryFormsRv.setAdapter(mEnquiryFormsAdapter);
    }

    @OnClick({R.id.fab_add_enquiry_form})
    public void onClickAddEnquiry() {
        openEnquiryFormFragment();
    }

    private void openEnquiryFormFragment() {
        EnquiryFormFragment fragment = new EnquiryFormFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment, CURRENT_TAG);
        fragmentTransaction.addToBackStack(CURRENT_TAG);

        fragmentTransaction.commit();
    }

    @Override
    public void getEnquiryFormReponse(boolean isSuccess, ArrayList<EnquiryFormsResponseBean> list) {
        if (isSuccess) {
            if (list.size() == 0) {
                mNoEnquiryFormTv.setVisibility(View.VISIBLE);

            } else {
                mNoEnquiryFormTv.setVisibility(View.GONE);
                mLinearLayout.setVisibility(View.VISIBLE);
                mEnquiryFormsAdapter.setEnquiryFormsList(list);
                mEnquiryFormsAdapter.notifyDataSetChanged();
            }
        } else {
            mEnquiryFormsRv.setVisibility(View.GONE);
            mNoEnquiryFormTv.setVisibility(View.VISIBLE);
        }
        DismissDialog.dismissWithCheck(mProgressDialog);
    }

    @Override
    public void getDataOfEnquirySelection(String enquiryId) {
        if (enquiryId != null && !enquiryId.isEmpty()) {
            openPartSuggestionFragment(enquiryId);
        }
    }

    private void openPartSuggestionFragment(String enquiryId) {
        Fragment fragment = new PartSuggestionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IntentKeyConstants.TAG_ENQUIRY_ID, enquiryId);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment, CURRENT_TAG);
        fragmentTransaction.addToBackStack(CURRENT_TAG);
        fragmentTransaction.commit();
    }


}