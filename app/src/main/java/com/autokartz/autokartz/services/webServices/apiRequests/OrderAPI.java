package com.autokartz.autokartz.services.webServices.apiRequests;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.interfaces.OrderAPIResponseListener;
import com.autokartz.autokartz.services.connectionClasses.UserConnection;
import com.autokartz.autokartz.utils.apiRequestBeans.OrderDataBean;
import com.autokartz.autokartz.utils.apiResponses.ProductOrderAPIResponse;
import com.autokartz.autokartz.utils.retrofitAdapter.RetroFitAdapter;
import com.autokartz.autokartz.utils.util.AppToast;
import com.autokartz.autokartz.utils.util.InternetConnection;
import com.autokartz.autokartz.utils.util.Logger;
import com.autokartz.autokartz.utils.util.constants.AppConstantKeys;
import com.autokartz.autokartz.utils.util.constants.ServerApi;
import com.autokartz.autokartz.utils.util.dialogs.DismissDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Cortana on 1/11/2018.
 */

public class OrderAPI implements Callback<ProductOrderAPIResponse> {


    private Context mContext;
    private OrderAPIResponseListener mOrderListener;
    private static final String TAG = GetOrdersAPI.class.getName();
    private ProgressDialog mProgressDialog;

    public OrderAPI(Context context, OrderAPIResponseListener orderListener, ProgressDialog progressDialog) {
        mContext = context;
        mOrderListener = orderListener;
        mProgressDialog = progressDialog;
    }

    public void callOrderApi(OrderDataBean orderDataBean) {
        if (!InternetConnection.isInternetConnected(mContext)) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            AppToast.showToast(mContext, mContext.getResources().getString(R.string.err_no_internet));
            return;
        }
        UserConnection userConnection = RetroFitAdapter.createService(UserConnection.class, ServerApi.SERVER_URL);
        Call<ProductOrderAPIResponse> call = userConnection.callProductOrder(AppConstantKeys.CONTENT_TYPE, orderDataBean.getUserId(), orderDataBean.getTxnId(), orderDataBean.getAmount(), orderDataBean.getShipTime(), orderDataBean.getStatus(), orderDataBean.getProductInfo(), orderDataBean.getPaymentMode());
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ProductOrderAPIResponse> call, Response<ProductOrderAPIResponse> response) {
        String message = "";
        if (response.isSuccessful() && response.body().isSuccess()) {
            message = response.body().getMessage();
            AppToast.showToast(mContext, message);
            afterSuccessfullResponse(true, message);
        } else {
            afterSuccessfullResponse(false, message);
        }

    }

    @Override
    public void onFailure(Call<ProductOrderAPIResponse> call, Throwable t) {
        Logger.LogError(TAG, t.getMessage());
        DismissDialog.dismissWithCheck(mProgressDialog);
        AppToast.showToast(mContext, "Network Error");
    }

    private void afterSuccessfullResponse(boolean status, String message) {
        mOrderListener.getOrderResponse(status, message);
    }

}