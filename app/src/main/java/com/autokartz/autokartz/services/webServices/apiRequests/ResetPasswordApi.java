package com.autokartz.autokartz.services.webServices.apiRequests;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.interfaces.ForgotPasswordListsner;
import com.autokartz.autokartz.interfaces.ResetPasswordListsner;
import com.autokartz.autokartz.services.connectionClasses.UserConnection;
import com.autokartz.autokartz.utils.apiResponses.ForgotPasswordBeanResponse;
import com.autokartz.autokartz.utils.apiResponses.ForgotPasswordBeanResult;
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

public class ResetPasswordApi implements Callback<ForgotPasswordBeanResult> {


    private Context mContext;
    private ResetPasswordListsner mResetPasswordListsner;
    private static final String TAG = ResetPasswordApi.class.getName();
    private ProgressDialog mProgressDialog;

    public ResetPasswordApi(Context context, ResetPasswordListsner resetPasswordListsner, ProgressDialog progressDialog) {
        mContext = context;
        mResetPasswordListsner = resetPasswordListsner;
        mProgressDialog = progressDialog;
    }


    public void callResetPasswordApi(String userId, String password) {
        if (!InternetConnection.isInternetConnected(mContext)) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            AppToast.showToast(mContext, mContext.getResources().getString(R.string.err_no_internet));
            return;
        }
        UserConnection userConnection = RetroFitAdapter.createService(UserConnection.class, ServerApi.SERVER_URL);
        Call<ForgotPasswordBeanResult> call = userConnection.resetPassword(userId, password);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ForgotPasswordBeanResult> call, Response<ForgotPasswordBeanResult> response) {
        if (response.isSuccessful() && response.body().isSuccess()) {
            String message = response.body().getMessage();
            AppToast.showToast(mContext, "Your Password has been changed successfully..Please Login to continue");
            afterSuccessfullResponse(true);
        } else {
            afterSuccessfullResponse(false);
        }
    }

    @Override
    public void onFailure(Call<ForgotPasswordBeanResult> call, Throwable t) {
        Logger.LogError(TAG, t.getMessage());
        DismissDialog.dismissWithCheck(mProgressDialog);
        AppToast.showToast(mContext, "Network Error");
    }

    private void afterSuccessfullResponse(boolean success) {
        mResetPasswordListsner.resetPassword(success);
    }

}