package com.autokartz.autokartz.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.autokartz.autokartz.adapters.OrdersAdapter;
import com.autokartz.autokartz.interfaces.GetOrderDetailApiResponseListener;
import com.autokartz.autokartz.interfaces.GetOrdersApiResponseListener;
import com.autokartz.autokartz.interfaces.OrderAdapterListener;
import com.autokartz.autokartz.services.databases.preferences.AccountDetailHolder;
import com.autokartz.autokartz.services.webServices.apiRequests.GetOrderDetailAPI;
import com.autokartz.autokartz.services.webServices.apiRequests.GetOrdersAPI;
import com.autokartz.autokartz.utils.apiResponses.OrderIdResponse;
import com.autokartz.autokartz.utils.pojoClasses.OrderDetail;
import com.autokartz.autokartz.utils.util.constants.IntentKeyConstants;
import com.autokartz.autokartz.utils.util.dialogs.DismissDialog;
import com.autokartz.autokartz.utils.util.dialogs.ShowDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cortana on 1/3/2018.
 */

public class OrdersFragment extends Fragment implements GetOrdersApiResponseListener, OrderAdapterListener, GetOrderDetailApiResponseListener {


    private static final String CURRENT_TAG = OrdersFragment.class.getName();
    @BindView(R.id.orders_rv)
    RecyclerView mOrderRv;
    @BindView(R.id.order_no_order_tv)
    TextView mNoOrderTv;
    private Context mContext;
    private Activity mActivity;
    private OrdersAdapter mOrdersAdapter;
    private ArrayList<OrderIdResponse> mOrderIdList;
    private ProgressDialog mProgressDialog;
    private AccountDetailHolder mAccountDetailHolder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("Orders");
        init();
    }

    private void init() {
        initVariables();
        setRecyclerView();
        callGetOrdersApi();
    }

    private void callGetOrdersApi() {
        String userId = mAccountDetailHolder.getUserDetailBean().getUserId();
        mProgressDialog = ShowDialog.show(mContext, "", "Please Wait", true, false);
        GetOrdersAPI getOrdersAPI = new GetOrdersAPI(mContext, this, mProgressDialog);
        getOrdersAPI.getOrdersApi(userId);
    }

    private void setRecyclerView() {
        mOrdersAdapter = new OrdersAdapter(mContext, this, mOrderIdList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mOrderRv.setLayoutManager(layoutManager);
        mOrderRv.setItemAnimator(new DefaultItemAnimator());
        mOrderRv.setAdapter(mOrdersAdapter);
    }

    private void initVariables() {
        mContext = getContext();
        mActivity = getActivity();
        mOrderIdList = new ArrayList<>();
        mAccountDetailHolder = new AccountDetailHolder(mContext);
    }

    @Override
    public void getOrderResponse(boolean isReceived, ArrayList<OrderIdResponse> orderList) {
        if (isReceived) {
            if (orderList.size() > 0) {
                mOrdersAdapter.setOrderIdList(orderList);
                mOrdersAdapter.notifyDataSetChanged();
            }
        } else {
            mNoOrderTv.setVisibility(View.VISIBLE);
        }
        DismissDialog.dismissWithCheck(mProgressDialog);
    }

    @Override
    public void getOrderIdSelected(String orderId) {
        String userId = mAccountDetailHolder.getUserDetailBean().getUserId();
        mProgressDialog = ShowDialog.show(mContext, "", "Please Wait", true, false);
        GetOrderDetailAPI getOrderDetailAPI = new GetOrderDetailAPI(mContext, this, mProgressDialog);
        getOrderDetailAPI.getOrdersApi(userId, orderId);

    }

    private void openDetailFragment(OrderDetail orderDetail) {
        Fragment fragment = new OrderDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentKeyConstants.TAG_ORDER_ID, orderDetail);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment, CURRENT_TAG);
        fragmentTransaction.addToBackStack(CURRENT_TAG);
        fragmentTransaction.commit();
    }

    @Override
    public void getOrderDetailResponse(boolean isReceived, OrderDetail orderDetail) {
        DismissDialog.dismissWithCheck(mProgressDialog);
        if (isReceived) {
            openDetailFragment(orderDetail);
        }
    }

}