package com.autokartz.autokartz.services.webServices.apiRequests;

import android.app.ProgressDialog;
import android.content.Context;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.interfaces.FcmTokenResponseListsner;
import com.autokartz.autokartz.services.connectionClasses.UserConnection;
import com.autokartz.autokartz.utils.apiResponses.UserDetailBeanResult;
import com.autokartz.autokartz.utils.retrofitAdapter.RetroFitAdapter;
import com.autokartz.autokartz.utils.util.AppToast;
import com.autokartz.autokartz.utils.util.InternetConnection;
import com.autokartz.autokartz.utils.util.Logger;
import com.autokartz.autokartz.utils.util.constants.ServerApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Cortana on 1/11/2018.
 */

public class BankInfoApi implements Callback<UserDetailBeanResult> {


    private Context mContext;
    private FcmTokenResponseListsner mFcmTokenListener;
    private static final String TAG = GetOrdersAPI.class.getName();
    ProgressDialog mProgressDialog;

    public BankInfoApi(Context context, FcmTokenResponseListsner tokenListener, ProgressDialog mProgressDialog) {
        mContext = context;
        mFcmTokenListener = tokenListener;
        mProgressDialog = mProgressDialog;


    }

    public void postBankInfo(String userId, String accountNo, String ifsc, String branch, String pan, String gst) {
        if (!InternetConnection.isInternetConnected(mContext)) {
            AppToast.showToast(mContext, mContext.getResources().getString(R.string.err_no_internet));
            return;
        }
        UserConnection userConnection = RetroFitAdapter.createService(UserConnection.class, ServerApi.SERVER_URL);
        Call<UserDetailBeanResult> call = userConnection.UserBankInformation(userId, accountNo, ifsc, branch, pan, gst);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<UserDetailBeanResult> call, Response<UserDetailBeanResult> response) {
        String message = "";
        if (response.isSuccessful() && response.body().isSuccess()) {
            message = response.body().getMessage();
            AppToast.showToast(mContext,message);
            afterSuccessfullResponse(true);
        } else {
            AppToast.showToast(mContext,message);
            afterSuccessfullResponse(false);
        }
    }

    @Override
    public void onFailure(Call<UserDetailBeanResult> call, Throwable t) {
        Logger.LogError(TAG, t.getMessage());
    }

    private void afterSuccessfullResponse(boolean success) {
        mFcmTokenListener.fcmTokenResponse(success);
    }

}