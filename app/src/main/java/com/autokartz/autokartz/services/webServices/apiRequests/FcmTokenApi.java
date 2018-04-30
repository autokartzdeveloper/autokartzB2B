package com.autokartz.autokartz.services.webServices.apiRequests;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.interfaces.FcmTokenResponseListsner;
import com.autokartz.autokartz.interfaces.OrderAPIResponseListener;
import com.autokartz.autokartz.services.connectionClasses.UserConnection;
import com.autokartz.autokartz.utils.apiRequestBeans.FcmTokenBean;
import com.autokartz.autokartz.utils.apiRequestBeans.OrderDataBean;
import com.autokartz.autokartz.utils.apiResponses.ProductOrderAPIResponse;
import com.autokartz.autokartz.utils.apiResponses.UserDetailBeanResult;
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

public class FcmTokenApi implements Callback<UserDetailBeanResult> {


    private Context mContext;
    private FcmTokenResponseListsner mFcmTokenListener;
    private static final String TAG = GetOrdersAPI.class.getName();

    public FcmTokenApi(Context context, FcmTokenResponseListsner tokenListener) {
        mContext = context;
        mFcmTokenListener = tokenListener;

    }

    public void callFcmTokenApi(String token, String userId) {
        if (!InternetConnection.isInternetConnected(mContext)) {
            AppToast.showToast(mContext, mContext.getResources().getString(R.string.err_no_internet));
            return;
        }
        UserConnection userConnection = RetroFitAdapter.createService(UserConnection.class, ServerApi.SERVER_URL);
        Call<UserDetailBeanResult> call = userConnection.userFcmToken(token, userId);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<UserDetailBeanResult> call, Response<UserDetailBeanResult> response) {
        String message = "";
        if (response.isSuccessful() && response.body().isSuccess()) {
            message = response.body().getMessage();
            afterSuccessfullResponse(true);
        } else {
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