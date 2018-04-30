package com.autokartz.autokartz.services.webServices.apiRequests;

import android.app.ProgressDialog;
import android.content.Context;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.interfaces.GetOrdersApiResponseListener;
import com.autokartz.autokartz.services.connectionClasses.UserConnection;
import com.autokartz.autokartz.utils.apiResponses.OrderApiResponse;
import com.autokartz.autokartz.utils.apiResponses.OrderIdResponse;
import com.autokartz.autokartz.utils.retrofitAdapter.RetroFitAdapter;
import com.autokartz.autokartz.utils.util.AppToast;
import com.autokartz.autokartz.utils.util.InternetConnection;
import com.autokartz.autokartz.utils.util.Logger;
import com.autokartz.autokartz.utils.util.constants.ServerApi;
import com.autokartz.autokartz.utils.util.dialogs.DismissDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Cortana on 1/11/2018.
 */

public class GetOrdersAPI implements Callback<OrderApiResponse> {


    private Context mContext;
    private GetOrdersApiResponseListener mOrderListener;
    private static final String TAG = GetOrdersAPI.class.getName();
    private ProgressDialog mProgressDialog;

    public GetOrdersAPI(Context context, GetOrdersApiResponseListener orderListener, ProgressDialog progressDialog) {
        mContext = context;
        mOrderListener = orderListener;
        mProgressDialog = progressDialog;
    }

    public void getOrdersApi(String userId) {
        if (!InternetConnection.isInternetConnected(mContext)) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            AppToast.showToast(mContext, mContext.getResources().getString(R.string.err_no_internet));
            return;
        }
        UserConnection userConnection = RetroFitAdapter.createService(UserConnection.class, ServerApi.SERVER_URL);
        Call<OrderApiResponse> call = userConnection.getOrdersList(userId);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<OrderApiResponse> call, Response<OrderApiResponse> response) {
        ArrayList<OrderIdResponse> orderIdList = new ArrayList<>();
        if (response.isSuccessful() && response.body().isSuccess()) {
            orderIdList = response.body().getOrderIdList();
            afterSuccessfullResponse(true, orderIdList);
        } else {
            afterSuccessfullResponse(false, orderIdList);
        }
    }

    @Override
    public void onFailure(Call<OrderApiResponse> call, Throwable t) {
        Logger.LogError(TAG, t.getMessage());
        DismissDialog.dismissWithCheck(mProgressDialog);
        AppToast.showToast(mContext, "Network Error");
    }

    private void afterSuccessfullResponse(boolean status, ArrayList<OrderIdResponse> orderList) {
        mOrderListener.getOrderResponse(status, orderList);
    }

}