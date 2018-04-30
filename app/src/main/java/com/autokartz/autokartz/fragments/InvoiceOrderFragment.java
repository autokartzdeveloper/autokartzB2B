package com.autokartz.autokartz.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.activities.MainDashboardActivity;
import com.autokartz.autokartz.activities.PaymentPayUActivity;
import com.autokartz.autokartz.adapters.InvoiceOrderItemAdapter;
import com.autokartz.autokartz.interfaces.OrderAPIResponseListener;
import com.autokartz.autokartz.services.databases.preferences.AccountDetailHolder;
import com.autokartz.autokartz.services.webServices.apiRequests.OrderAPI;
import com.autokartz.autokartz.utils.apiRequestBeans.OrderDataBean;
import com.autokartz.autokartz.utils.apiResponses.SuggestionResponseBean;
import com.autokartz.autokartz.utils.util.AppToast;
import com.autokartz.autokartz.utils.util.constants.IntentKeyConstants;
import com.autokartz.autokartz.utils.util.dialogs.DismissDialog;
import com.autokartz.autokartz.utils.util.dialogs.ShowDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 3/6/2018.
 */

public class InvoiceOrderFragment extends Fragment implements OrderAPIResponseListener {

    @BindView(R.id.invoice_order_total_amount_val_tv)
    TextView mAmount;
    @BindView(R.id.invoice_order_rv)
    RecyclerView mItemRv;
    @BindView(R.id.invoice_order_cancel_btn)
    Button mcancelBtn;
    @BindView(R.id.invoice_order_pay_btn)
    Button mPayBtn;
    private Context mContext;
    private Activity mActivity;
    private InvoiceOrderItemAdapter mInvoiceOrderItemAdapter;
    private ArrayList<SuggestionResponseBean> mOrderList;
    private ProgressDialog mProgressDialog;
    private AccountDetailHolder mAccountDetailHolder;
    String codeStatus;
    int totalPrice = 0;
    int shipingCharge = 0;
    int totalPayableAmout;
    int tax;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invoice_order, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("Invoice Order");
        init();
    }

    private void init() {
        mContext = getContext();
        mActivity = getActivity();
        mOrderList = new ArrayList<>();
        mAccountDetailHolder = new AccountDetailHolder(mContext);
        getData();
        setRecyclerView();
        getTotalPrice();
        mAmount.setText("Rs."+String.valueOf(totalPayableAmout));

    }

    //Calculating item total price
    public int getTotalPrice() {
        for (int i = 0; i < mOrderList.size(); i++) {
            totalPrice = Integer.parseInt(mOrderList.get(i).getPrice());
            shipingCharge = Integer.parseInt(mOrderList.get(i).getShipCharges());
            tax = Integer.parseInt(mOrderList.get(i).getTax());
            int cal = (totalPrice / 100) * tax;
            totalPayableAmout += totalPrice + shipingCharge + cal;
        }
        return (totalPayableAmout);
    }


    private void getData() {
        codeStatus = getArguments().getString(IntentKeyConstants.TAG_ORDER_COD);
        Gson gson = new Gson();
        String jsonDetails = getArguments().getString(IntentKeyConstants.TAG_ORDER_LIST);

        Type type = new TypeToken<ArrayList<SuggestionResponseBean>>() {
        }.getType();
        mOrderList = gson.fromJson(jsonDetails, type);


    }

    private void setRecyclerView() {
        mInvoiceOrderItemAdapter = new InvoiceOrderItemAdapter(mContext, mOrderList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mItemRv.setLayoutManager(layoutManager);
        mItemRv.setItemAnimator(new DefaultItemAnimator());
        mItemRv.setAdapter(mInvoiceOrderItemAdapter);
    }

    private OrderDataBean getOrderDataBean() {
        String userId = mAccountDetailHolder.getUserDetailBean().getUserId();
        String txnId = "";
        String amount = String.valueOf(totalPayableAmout);
        int shipTime = 5;
        String prdtInfo = "";
        String paymentMode = "";
        for (int i = 0; i < mOrderList.size(); i++) {
            prdtInfo = prdtInfo + mOrderList.get(i).getId();
            if (i < mOrderList.size() - 1) {
                prdtInfo = prdtInfo + ",";
            }
        }
        String status = "2";
        return new OrderDataBean(userId, txnId, amount, shipTime, status, prdtInfo, paymentMode);
    }

    @OnClick({R.id.invoice_order_cancel_btn})
    public void onClickCancelBtn() {
        mProgressDialog = ShowDialog.show(mContext, "", "Please Wait", true, false);
        OrderAPI orderAPI = new OrderAPI(mContext, this, mProgressDialog);
        orderAPI.callOrderApi(getOrderDataBean());
    }

    @OnClick({R.id.invoice_order_pay_btn})
    public void onClickPayBtn() {
        Intent paymentIntent = new Intent(mActivity, PaymentPayUActivity.class);
        paymentIntent.putExtra("orderDataBean", getOrderDataBean());
        paymentIntent.putExtra("codestatus",codeStatus);
        startActivity(paymentIntent);
    }

    //for cancel order
    @Override
    public void getOrderResponse(boolean isOrdered, String message) {
        Intent intent = new Intent(mActivity, MainDashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);


    }

}