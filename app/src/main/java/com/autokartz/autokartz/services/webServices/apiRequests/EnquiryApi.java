package com.autokartz.autokartz.services.webServices.apiRequests;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.interfaces.GetEnquiryApiResponseListener;
import com.autokartz.autokartz.services.connectionClasses.UserConnection;
import com.autokartz.autokartz.utils.apiResponses.EnquiryResponseBean;
import com.autokartz.autokartz.utils.pojoClasses.CarInformation;
import com.autokartz.autokartz.utils.retrofitAdapter.RetroFitAdapter;
import com.autokartz.autokartz.utils.util.AppToast;
import com.autokartz.autokartz.utils.util.InternetConnection;
import com.autokartz.autokartz.utils.util.Logger;
import com.autokartz.autokartz.utils.util.constants.ServerApi;
import com.autokartz.autokartz.utils.util.dialogs.DismissDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 2/21/2018.
 */

public class EnquiryApi implements Callback<EnquiryResponseBean> {


    private Context mContext;
    private static final String TAG = ManualLoginApi.class.getName();
    private ProgressDialog mProgressDialog;
    private GetEnquiryApiResponseListener mGetEnquiryApiResponseListener;

    public EnquiryApi(Context context, GetEnquiryApiResponseListener getEnquiryApiResponseListener, ProgressDialog progressDialog) {
        mContext = context;
        mProgressDialog = progressDialog;
        mGetEnquiryApiResponseListener = getEnquiryApiResponseListener;
    }

    public void callEnquiryApi(CarInformation carInfo) {
        Gson gson = new Gson();
        String jsonDetails = gson.toJson(carInfo);
        Logger.LogDebug("hello", jsonDetails);
        if (!InternetConnection.isInternetConnected(mContext)) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            AppToast.showToast(mContext, mContext.getResources().getString(R.string.err_no_internet));
            return;
        }
        UserConnection userConnection = RetroFitAdapter.createService(UserConnection.class, ServerApi.SERVER_URL);
        Call<EnquiryResponseBean> call = userConnection.callEnuiryApi(jsonDetails);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<EnquiryResponseBean> call, Response<EnquiryResponseBean> response) {

        if (response.isSuccessful() && response.body().isSuccess()) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            AppToast.showToast(mContext, response.body().getMessage());
            afterSuccessfullResponse(true);
        } else {
            DismissDialog.dismissWithCheck(mProgressDialog);
            AppToast.showToast(mContext, response.body().getMessage());
            afterSuccessfullResponse(false);
        }
    }

    @Override
    public void onFailure(Call<EnquiryResponseBean> call, Throwable t) {
        Logger.LogError(TAG, t.getMessage());
        DismissDialog.dismissWithCheck(mProgressDialog);
        AppToast.showToast(mContext, "Network Error");
    }

    private void afterSuccessfullResponse(boolean status) {
        mGetEnquiryApiResponseListener.getEnquiryApiResponse(status);
    }

}