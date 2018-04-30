package com.autokartz.autokartz.services.webServices.apiRequests;

import android.app.ProgressDialog;
import android.content.Context;
import com.autokartz.autokartz.R;
import com.autokartz.autokartz.interfaces.SuggestionResponseListener;
import com.autokartz.autokartz.services.connectionClasses.UserConnection;
import com.autokartz.autokartz.utils.apiResponses.EnquirySuggestionResponseBean;
import com.autokartz.autokartz.utils.apiResponses.ProductSuggestionResponseBean;
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
 * Created by user on 3/5/2018.
 */

public class SuggestionApi implements Callback<ProductSuggestionResponseBean>{


    private Context mContext;
    private ProgressDialog mProgressDialog;
    private SuggestionResponseListener mSuggestionResponseListener;
    private static final String TAG=SuggestionApi.class.getName();

    public SuggestionApi(Context context,ProgressDialog progressDialog,SuggestionResponseListener listener) {
        mContext=context;
        mProgressDialog=progressDialog;
        mSuggestionResponseListener=listener;
    }

    public void callSuggestionApi(String userId, String enquiryId) {
        if(!InternetConnection.isInternetConnected(mContext)) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            AppToast.showToast(mContext,mContext.getResources().getString(R.string.err_no_internet));
            return;
        }
        UserConnection userConnection= RetroFitAdapter.createService(UserConnection.class, ServerApi.SERVER_URL);
        Call<ProductSuggestionResponseBean> call=userConnection.getSuggestions(userId,enquiryId);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ProductSuggestionResponseBean> call, Response<ProductSuggestionResponseBean> response) {
        if(response.isSuccessful()&&response.body().isSuccess()) {
            EnquirySuggestionResponseBean enquirySuggestionResponseBean=response.body().getEnquirySuggestionResponseBean();
            afterSuccessFullResponse(enquirySuggestionResponseBean,true);
        } else {
            AppToast.showToast(mContext,"Data not received");
            DismissDialog.dismissWithCheck(mProgressDialog);
        }
    }

    @Override
    public void onFailure(Call<ProductSuggestionResponseBean> call, Throwable t) {
        Logger.LogError(TAG, t.getMessage());
        DismissDialog.dismissWithCheck(mProgressDialog);
        AppToast.showToast(mContext,"Network Error");
        afterSuccessFullResponse(null,false);
    }

    private void afterSuccessFullResponse(EnquirySuggestionResponseBean enquirySuggestionResponseBean, boolean isReceived) {
        mSuggestionResponseListener.getSuggestionResponse(enquirySuggestionResponseBean,isReceived);
    }

}