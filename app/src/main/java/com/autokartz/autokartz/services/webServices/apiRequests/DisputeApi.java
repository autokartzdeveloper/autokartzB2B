package com.autokartz.autokartz.services.webServices.apiRequests;

import android.app.ProgressDialog;
import android.content.Context;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.interfaces.DisputeApiListener;
import com.autokartz.autokartz.interfaces.SignUpResponseListsner;
import com.autokartz.autokartz.services.connectionClasses.UserConnection;
import com.autokartz.autokartz.utils.apiRequestBeans.DisputeDataBean;
import com.autokartz.autokartz.utils.apiRequestBeans.SignUpBean;
import com.autokartz.autokartz.utils.apiResponses.DisputeResponse;
import com.autokartz.autokartz.utils.apiResponses.UserDetailBean;
import com.autokartz.autokartz.utils.apiResponses.UserDetailBeanResult;
import com.autokartz.autokartz.utils.pojoClasses.UserDetails;
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

public class DisputeApi implements Callback<DisputeResponse> {

    private Context mContext;
    private DisputeApiListener mDisputeApiResponseListsner;
    private static final String TAG = DisputeApi.class.getName();
    private ProgressDialog mProgressDialog;

    public DisputeApi(Context context, DisputeApiListener disputeResponseListsner, ProgressDialog progressDialog) {
        mContext = context;
        mDisputeApiResponseListsner = disputeResponseListsner;
        mProgressDialog = progressDialog;
    }

    public void callDisputeApi(DisputeDataBean disputeDataBean) {
        if (!InternetConnection.isInternetConnected(mContext)) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            AppToast.showToast(mContext, mContext.getResources().getString(R.string.err_no_internet));
            return;
        }
        UserConnection userConnection = RetroFitAdapter.createService(UserConnection.class, ServerApi.SERVER_URL);
        Call<DisputeResponse> call = userConnection.newDisputeForm(disputeDataBean.getUserId(), disputeDataBean.getOrderId(), disputeDataBean.getSuggestionId(), disputeDataBean.getDisputeReason(), disputeDataBean.getDisputeDetail());
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<DisputeResponse> call, Response<DisputeResponse> response) {
        if (response.isSuccessful() && response.body().isSuccess()) {
            AppToast.showToast(mContext, response.body().getMessage());
            afterSuccessfullResponse(true);
        } else {
            AppToast.showToast(mContext, response.body().getMessage());
            afterSuccessfullResponse(false);
        }
    }

    @Override
    public void onFailure(Call<DisputeResponse> call, Throwable t) {
        Logger.LogError(TAG, t.getMessage());
        DismissDialog.dismissWithCheck(mProgressDialog);
        AppToast.showToast(mContext, "Network Error");
    }

    private void afterSuccessfullResponse(boolean success) {
        mDisputeApiResponseListsner.disputeResponse(success);
    }

}