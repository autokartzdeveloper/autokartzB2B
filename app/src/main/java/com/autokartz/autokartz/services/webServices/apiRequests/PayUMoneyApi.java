package com.autokartz.autokartz.services.webServices.apiRequests;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.autokartz.autokartz.interfaces.GetPayUMoneyApiResponseListener;
import com.autokartz.autokartz.services.connectionClasses.UserConnection;
import com.autokartz.autokartz.utils.apiRequestBeans.PayUMoneyRequestBean;
import com.autokartz.autokartz.utils.apiResponses.PayUMoneyResponseBean;
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
import com.autokartz.autokartz.R;

/**
 * Created by user on 3/10/2018.
 */

public class PayUMoneyApi implements Callback<PayUMoneyResponseBean>{

    private Context mContext;
    private GetPayUMoneyApiResponseListener moneyApiResponseListener;
    private static final String TAG=ManualLoginApi.class.getName();
    private ProgressDialog mProgressDialog;

    public PayUMoneyApi(Context context, GetPayUMoneyApiResponseListener listener, ProgressDialog progressDialog) {
        mContext=context;
        moneyApiResponseListener=listener;
        mProgressDialog=progressDialog;
    }

    public void callPayUMoneyApi(PayUMoneyRequestBean payUMoneyRequestBean) {
        if(!InternetConnection.isInternetConnected(mContext)) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            AppToast.showToast(mContext,mContext.getResources().getString(R.string.err_no_internet));
            return;
        }
        UserConnection userConnection= RetroFitAdapter.createService(UserConnection.class, ServerApi.SERVER_URL);
        Call<PayUMoneyResponseBean> call=userConnection.getPayUMoneyHash(AppConstantKeys.CONTENT_TYPE,payUMoneyRequestBean.getKey(),payUMoneyRequestBean.getSalt(),payUMoneyRequestBean.getAmount(),payUMoneyRequestBean.getProductinfo(),payUMoneyRequestBean.getFirstname(),payUMoneyRequestBean.getEmail());
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<PayUMoneyResponseBean> call, Response<PayUMoneyResponseBean> response) {
        PayUMoneyResponseBean responseBean=new PayUMoneyResponseBean();

        if(response.isSuccessful()&&response.body().isSuccess()) {
            responseBean=response.body();
            AppToast.showToast(mContext,"Hash Generated Successfully.");
            afterSuccessfullResponse(true,responseBean);
        } else {
            AppToast.showToast(mContext,"Hash Generation Failed");
            afterSuccessfullResponse(false,responseBean);
        }
    }

    @Override
    public void onFailure(Call<PayUMoneyResponseBean> call, Throwable t) {
        Logger.LogError(TAG,t.getMessage());
        DismissDialog.dismissWithCheck(mProgressDialog);
        AppToast.showToast(mContext,"Network Error");
    }

    private void afterSuccessfullResponse(boolean status,PayUMoneyResponseBean responseBean) {
        moneyApiResponseListener.getPayUApiResponse(status,responseBean);
    }

}