package com.autokartz.autokartz.services.webServices.apiRequests;

import android.app.ProgressDialog;
import android.content.Context;
import com.autokartz.autokartz.R;
import com.autokartz.autokartz.interfaces.GetOrderDetailApiResponseListener;
import com.autokartz.autokartz.services.connectionClasses.UserConnection;
import com.autokartz.autokartz.utils.apiResponses.OrderDetailApiResponse;
import com.autokartz.autokartz.utils.pojoClasses.OrderDetail;
import com.autokartz.autokartz.utils.retrofitAdapter.RetroFitAdapter;
import com.autokartz.autokartz.utils.util.AppToast;
import com.autokartz.autokartz.utils.util.InternetConnection;
import com.autokartz.autokartz.utils.util.Logger;
import com.autokartz.autokartz.utils.util.constants.ServerApi;
import com.autokartz.autokartz.utils.util.dialogs.DismissDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Cortana on 1/11/2018.
 */

public class GetOrderDetailAPI implements Callback<OrderDetailApiResponse> {


    private Context mContext;
    private GetOrderDetailApiResponseListener mOrderListener;
    private static final String TAG=GetOrderDetailAPI.class.getName();
    private ProgressDialog mProgressDialog;

    public GetOrderDetailAPI(Context context,GetOrderDetailApiResponseListener orderListener,ProgressDialog progressDialog) {
        mContext=context;
        mOrderListener=orderListener;
        mProgressDialog=progressDialog;
    }

    public void getOrdersApi(String userId,String orderId) {
        if(!InternetConnection.isInternetConnected(mContext)) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            AppToast.showToast(mContext,mContext.getResources().getString(R.string.err_no_internet));
            return;
        }
        UserConnection userConnection= RetroFitAdapter.createService(UserConnection.class, ServerApi.SERVER_URL);
        Call<OrderDetailApiResponse> call=userConnection.getOrderDetails(userId,orderId);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<OrderDetailApiResponse> call, Response<OrderDetailApiResponse> response) {
        OrderDetail orderDetail=new OrderDetail();
        if(response.isSuccessful()&&response.body().isSuccess()) {
            orderDetail=response.body().getDetail();
            afterSuccessfullResponse(true,orderDetail);
        } else {
            afterSuccessfullResponse(false,orderDetail);
        }
    }

    @Override
    public void onFailure(Call<OrderDetailApiResponse> call, Throwable t) {
        Logger.LogError(TAG,t.getMessage());
        DismissDialog.dismissWithCheck(mProgressDialog);
        AppToast.showToast(mContext,"Network Error");
    }

    private void afterSuccessfullResponse(boolean status,OrderDetail orderDetail) {
        mOrderListener.getOrderDetailResponse(status,orderDetail);
    }

}