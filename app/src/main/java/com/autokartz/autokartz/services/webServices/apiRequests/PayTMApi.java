package com.autokartz.autokartz.services.webServices.apiRequests;

import android.app.ProgressDialog;
import android.content.Context;

import com.autokartz.autokartz.interfaces.GetPayTMApiResponseListener;
import com.autokartz.autokartz.services.connectionClasses.UserConnection;
import com.autokartz.autokartz.utils.apiRequestBeans.PayTmRequestBean;
import com.autokartz.autokartz.utils.apiResponses.PayTmResponseBean;
import com.autokartz.autokartz.utils.retrofitAdapter.RetroFitAdapter;
import com.autokartz.autokartz.utils.util.AppToast;
import com.autokartz.autokartz.utils.util.InternetConnection;
import com.autokartz.autokartz.utils.util.Logger;
import com.autokartz.autokartz.utils.util.constants.AppConstantKeys;
import com.autokartz.autokartz.utils.util.constants.ServerApi;
import com.autokartz.autokartz.utils.util.dialogs.DismissDialog;
import com.autokartz.autokartz.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 3/10/2018.
 */
public class PayTMApi implements Callback<PayTmResponseBean> {

    private Context mContext;
    private GetPayTMApiResponseListener moneyApiResponseListener;
    private static final String TAG = ManualLoginApi.class.getName();
    private ProgressDialog mProgressDialog;

    public PayTMApi(Context context, GetPayTMApiResponseListener listener, ProgressDialog progressDialog) {
        mContext = context;
        moneyApiResponseListener = listener;
        mProgressDialog = progressDialog;
    }

    public void callPayTMApi(PayTmRequestBean payTmRequestBean) {
        if (!InternetConnection.isInternetConnected(mContext)) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            AppToast.showToast(mContext, mContext.getResources().getString(R.string.err_no_internet));
            return;
        }
        UserConnection userConnection = RetroFitAdapter.createService(UserConnection.class, ServerApi.SERVER_URL);
        Call<PayTmResponseBean> call = userConnection.getPaytmHash(AppConstantKeys.CONTENT_TYPE, payTmRequestBean.getmID(), payTmRequestBean.getCustid(), payTmRequestBean.getIndustryTypeId(), payTmRequestBean.getChannelId(), payTmRequestBean.getTxnAmount(), payTmRequestBean.getWebsite(), payTmRequestBean.getMerchantKey());
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<PayTmResponseBean> call, Response<PayTmResponseBean> response) {
        PayTmResponseBean responseBean = new PayTmResponseBean();
        if (response.isSuccessful() && response.body().isSuccess()) {
            responseBean = response.body();
            AppToast.showToast(mContext, "Hash Generated Successfully.");
            afterSuccessfullResponse(true, responseBean);
        } else {
            AppToast.showToast(mContext, "Hash Generation Failed");
            afterSuccessfullResponse(false, responseBean);
        }
    }

    @Override
    public void onFailure(Call<PayTmResponseBean> call, Throwable t) {
        Logger.LogError(TAG, t.getMessage());
        DismissDialog.dismissWithCheck(mProgressDialog);
        AppToast.showToast(mContext, "Network Error");
    }

    private void afterSuccessfullResponse(boolean status, PayTmResponseBean responseBean) {
        moneyApiResponseListener.getPayTmApiResponse(status, responseBean);
    }

}