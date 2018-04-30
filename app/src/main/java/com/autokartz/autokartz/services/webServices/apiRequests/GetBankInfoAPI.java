package com.autokartz.autokartz.services.webServices.apiRequests;

import android.app.ProgressDialog;
import android.content.Context;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.interfaces.BankInfoResponseListener;
import com.autokartz.autokartz.interfaces.GetOrdersApiResponseListener;
import com.autokartz.autokartz.services.connectionClasses.UserConnection;
import com.autokartz.autokartz.utils.apiResponses.OrderApiResponse;
import com.autokartz.autokartz.utils.apiResponses.OrderIdResponse;
import com.autokartz.autokartz.utils.apiResponses.UserBankDetailResult;
import com.autokartz.autokartz.utils.apiResponses.UserBankInfo;
import com.autokartz.autokartz.utils.apiResponses.UserDetailBean;
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

public class GetBankInfoAPI implements Callback<UserBankDetailResult> {


    private Context mContext;
    private BankInfoResponseListener mOrderListener;
    private static final String TAG = GetBankInfoAPI.class.getName();
    private ProgressDialog mProgressDialog;
    UserBankInfo userBankInfo;

    public GetBankInfoAPI(Context context, BankInfoResponseListener orderListener, ProgressDialog progressDialog) {
        mContext = context;
        mOrderListener = orderListener;
        mProgressDialog = progressDialog;
    }

    public void getBankDetailApi(String userId) {
        if (!InternetConnection.isInternetConnected(mContext)) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            AppToast.showToast(mContext, mContext.getResources().getString(R.string.err_no_internet));
            return;
        }
        UserConnection userConnection = RetroFitAdapter.createService(UserConnection.class, ServerApi.SERVER_URL);
        Call<UserBankDetailResult> call = userConnection.getBankInfo(userId);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<UserBankDetailResult> call, Response<UserBankDetailResult> response) {
        if (response.isSuccessful() && response.body().isSuccess()) {
            String message = response.message();
            UserBankInfo userBankInfo = response.body().getUserBankInfo();
            afterSuccessfullResponse(true, userBankInfo);
        } else {
            String message = response.message();
            UserBankInfo userBankInfo = response.body().getUserBankInfo();
            afterSuccessfullResponse(false, userBankInfo);
        }
    }

    @Override
    public void onFailure(Call<UserBankDetailResult> call, Throwable t) {
        Logger.LogError(TAG, t.getMessage());
        DismissDialog.dismissWithCheck(mProgressDialog);
        AppToast.showToast(mContext, "Network Error");
    }

    private void afterSuccessfullResponse(boolean success, UserBankInfo userBankInfo) {
        mOrderListener.getBankInfoResponse(success, userBankInfo);
    }

}