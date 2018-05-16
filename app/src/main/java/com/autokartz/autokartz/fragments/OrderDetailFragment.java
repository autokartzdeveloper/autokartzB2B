package com.autokartz.autokartz.fragments;

import android.app.Activity;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.adapters.OrderDetailItemAdapter;
import com.autokartz.autokartz.utils.apiResponses.SuggestionResponseBean;
import com.autokartz.autokartz.utils.converter.ConvertDateFormat;
import com.autokartz.autokartz.utils.pojoClasses.OrderDetail;
import com.autokartz.autokartz.utils.util.Logger;
import com.autokartz.autokartz.utils.util.constants.IntentKeyConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cortana on 3/14/2018.
 */

public class OrderDetailFragment extends Fragment {

    @BindView(R.id.order_detail_date_val_tv)
    TextView mDateTv;
    @BindView(R.id.order_detail_status_val_tv)
    TextView mStatusTv;
    @BindView(R.id.order_detail_amt_val_tv)
    TextView mAmtTv;
    @BindView(R.id.order_detail_order_id_val_tv)
    TextView mOrderIdTv;
    @BindView(R.id.order_detail_items_rv)
    RecyclerView mItemsRv;
    private Context mContext;
    private Activity mActivity;
    private OrderDetailItemAdapter mOrderDetailItemAdapter;
    private ArrayList<SuggestionResponseBean> mSuggestionBeanResponse;
    private OrderDetail mOrderDetail;
    Date dateDelivery;
    Date dateCurrentTime;
    int disputeClosingDay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("Order Details");
        init();
    }

    private void init() {
        initVariables();
        getData();
        setViews();
        setRecyclerView();
    }

    private void setRecyclerView() {
        mOrderDetailItemAdapter = new OrderDetailItemAdapter(mContext, mActivity, mSuggestionBeanResponse, mOrderDetail, disputeClosingDay);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mItemsRv.setLayoutManager(layoutManager);
        mItemsRv.setItemAnimator(new DefaultItemAnimator());
        mItemsRv.setAdapter(mOrderDetailItemAdapter);
    }

    private void getData() {
        mOrderDetail = (OrderDetail) getArguments().getSerializable(IntentKeyConstants.TAG_ORDER_ID);
    }

    private void setViews() {
        mSuggestionBeanResponse = mOrderDetail.getProductInfo();
        Logger.LogDebug("hello", mOrderDetail.getProductInfo() + "");
        String date = mOrderDetail.getTimestamp();
        String deliveryUpdated = mOrderDetail.getDeliveryUpdated();
        if (deliveryUpdated != null && !(deliveryUpdated.isEmpty())) {
            //deliverydate
            String deliveryDate = ConvertDateFormat.convertDateTimeFormat(deliveryUpdated);
            //current date
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
            Date currentLocalTime = cal.getTime();
            DateFormat date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
            date1.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
            String currentDateTimeString = date1.format(currentLocalTime);
            //convert string in Date format
            try {
                dateDelivery = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").parse(deliveryDate);
                dateCurrentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").parse(currentDateTimeString);
                long difference = dateCurrentTime.getTime() - dateDelivery.getTime();
                //Find number of days by dividing the mili seconds
                disputeClosingDay = (int) (difference / (24 * 60 * 60 * 1000));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        mDateTv.setText(ConvertDateFormat.convertDateFormat(date));
        if (mOrderDetail.getStatus().matches("1")) {
            mStatusTv.setText("Order Success");
        } else {
            mStatusTv.setText("Order Failed");
        }
        mAmtTv.setText(mContext.getResources().getString(R.string.Rs) + mOrderDetail.getAmount());
        mOrderIdTv.setText(mOrderDetail.getOrderId() + "");
    }

    private void initVariables() {
        mContext = getContext();
        mActivity = getActivity();
        mOrderDetail = new OrderDetail();
        mSuggestionBeanResponse = new ArrayList<>();
    }

}