package com.autokartz.autokartz.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.activities.MainDashboardActivity;
import com.autokartz.autokartz.interfaces.DisputeApiListener;
import com.autokartz.autokartz.services.webServices.apiRequests.DisputeApi;
import com.autokartz.autokartz.services.webServices.apiRequests.ManualSignUpApi;
import com.autokartz.autokartz.services.webServices.apiRequests.OrderAPI;
import com.autokartz.autokartz.utils.apiRequestBeans.DisputeDataBean;
import com.autokartz.autokartz.utils.apiRequestBeans.SignUpBean;
import com.autokartz.autokartz.utils.apiResponses.UserDetailBean;
import com.autokartz.autokartz.utils.pojoClasses.OrderDetail;
import com.autokartz.autokartz.utils.util.constants.IntentKeyConstants;
import com.autokartz.autokartz.utils.util.dialogs.DismissDialog;
import com.autokartz.autokartz.utils.util.dialogs.ShowDialog;
import com.payumoney.sdkui.ui.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

/**
 * Created by user on 3/23/2018.
 */

public class DisputeFragment extends Fragment implements DisputeApiListener {

    private Context mContext;
    private Activity mActivity;
    @BindView(R.id.dispute_form_reason_spinner)
    Spinner mReasonSpinner;
    @BindView(R.id.dispute_form_detail_et)
    EditText mDetailEt;
    @BindView(R.id.dispute_form_submit_btn)
    Button mSubmitBtn;
    private ArrayAdapter<String> mReasonAdapter;
    private OrderDetail mOrderDetail;
    private ProgressDialog mProgressDialog;
    String disputeReason;
    String msuggestionID;
    String mDeliveryTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dispute_form, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
    }

    private void init() {
        initVariables();
        getData();
        setSpinnerAdapter();
    }

    private void getData() {
        mOrderDetail = (OrderDetail) getArguments().getSerializable(IntentKeyConstants.KEY_);
        msuggestionID = getArguments().getString("suggestion_id");


    }

    private void setSpinnerAdapter() {
        mReasonAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0 && super.isEnabled(position);
            }
        };
        mReasonAdapter.clear();
        mReasonAdapter.addAll(getResources().getStringArray(R.array.DISPUTE_REASONS));
        mReasonSpinner.setAdapter(mReasonAdapter);
    }

    @OnItemSelected({R.id.dispute_form_reason_spinner})
    public void onSelectedDisputeReason(AdapterView<?> parent, View view, int position, long id) {
        disputeReason = (parent.getItemAtPosition(position).toString());
    }

    private void initVariables() {
        mContext = getContext();
        mActivity = getActivity();
    }

    @OnClick({R.id.dispute_form_submit_btn})
    public void onSubmitDisputeBtn() {
        String disputeDetail = mDetailEt.getText().toString();
        String user_id = String.valueOf(mOrderDetail.getUserId());
        String order_id = String.valueOf(mOrderDetail.getOrderId());
        String suggestion_id = msuggestionID;
        if ((disputeDetail == null) && (disputeDetail.isEmpty())) {
            Toast.makeText(mContext, "Please enter Dispute details", Toast.LENGTH_SHORT).show();
            return;
        }
        if (disputeReason.matches("Select Reason")) {
            Toast.makeText(mContext, "Please enter Dispute Reason", Toast.LENGTH_SHORT).show();
            return;
        }
        DisputeDataBean disputeDataBean = new DisputeDataBean(user_id, order_id, suggestion_id, disputeReason, disputeDetail);
        mProgressDialog = ShowDialog.show(mContext, "", "Please Wait", true, false);
        DisputeApi disputeApi = new DisputeApi(mContext, this, mProgressDialog);
        disputeApi.callDisputeApi(disputeDataBean);

    }

    @Override
    public void disputeResponse(boolean success) {
        if (success) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            Intent intent = new Intent(mActivity, MainDashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            DismissDialog.dismissWithCheck(mProgressDialog);
        }
    }

}