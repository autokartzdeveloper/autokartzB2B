package com.autokartz.autokartz.services.webServices.apiRequests;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.interfaces.ForgotPasswordListsner;
import com.autokartz.autokartz.services.connectionClasses.UserConnection;
import com.autokartz.autokartz.utils.apiRequestBeans.ForgotPaswordBean;
import com.autokartz.autokartz.utils.apiRequestBeans.SignInBean;
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

public class ForgotPasswordApi implements Callback<ForgotPasswordBeanResult> {


    private Context mContext;
    private ForgotPasswordListsner mForgotPasswordListsner;
    private static final String TAG = ForgotPasswordApi.class.getName();
    private ProgressDialog mProgressDialog;

    public ForgotPasswordApi(Context context, ForgotPasswordListsner forgotPasswordListsner, ProgressDialog progressDialog) {
        mContext = context;
        mForgotPasswordListsner = forgotPasswordListsner;
        mProgressDialog = progressDialog;
    }


    public void callForgotPasswordApi(String email, String phone) {
        if (!InternetConnection.isInternetConnected(mContext)) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            AppToast.showToast(mContext, mContext.getResources().getString(R.string.err_no_internet));
            return;
        }
        UserConnection userConnection = RetroFitAdapter.createService(UserConnection.class, ServerApi.SERVER_URL);
        Call<ForgotPasswordBeanResult> call = userConnection.forgotPassword(email, phone);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ForgotPasswordBeanResult> call, Response<ForgotPasswordBeanResult> response) {
        ForgotPasswordBeanResponse forgotPasswordBeanResponse = new ForgotPasswordBeanResponse();
        if (response.isSuccessful() && response.body().isSuccess()) {
            forgotPasswordBeanResponse = response.body().getForgotPasswordBeanResponse();
            afterSuccessfullResponse(true, forgotPasswordBeanResponse);
        } else {
            afterSuccessfullResponse(false, forgotPasswordBeanResponse);
        }
    }

    @Override
    public void onFailure(Call<ForgotPasswordBeanResult> call, Throwable t) {
        Logger.LogError(TAG, t.getMessage());
        Log.v("asdf", t.getMessage());
        DismissDialog.dismissWithCheck(mProgressDialog);
        AppToast.showToast(mContext, "Network Error");
    }

    private void afterSuccessfullResponse(boolean success, ForgotPasswordBeanResponse forgotPasswordBeanResponse) {
        mForgotPasswordListsner.forgotPasswordListsner(success, forgotPasswordBeanResponse);
    }

}