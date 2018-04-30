package com.autokartz.autokartz.services.webServices.apiRequests;

import android.app.ProgressDialog;
import android.content.Context;
import com.autokartz.autokartz.R;
import com.autokartz.autokartz.interfaces.GetEnquiryFormsResponseListener;
import com.autokartz.autokartz.services.connectionClasses.UserConnection;
import com.autokartz.autokartz.utils.apiResponses.EnquiryFormsListResponseBean;
import com.autokartz.autokartz.utils.apiResponses.EnquiryFormsResponseBean;
import com.autokartz.autokartz.utils.retrofitAdapter.RetroFitAdapter;
import com.autokartz.autokartz.utils.util.AppToast;
import com.autokartz.autokartz.utils.util.InternetConnection;
import com.autokartz.autokartz.utils.util.constants.ServerApi;
import com.autokartz.autokartz.utils.util.dialogs.DismissDialog;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 2/26/2018.
 */

public class GetEnquiryFormsApi implements Callback<EnquiryFormsListResponseBean> {


    private Context mContext;
    private ProgressDialog mProgressDialog;
    private GetEnquiryFormsResponseListener mGetEnquiryFormsResponseListener;

    public GetEnquiryFormsApi(Context context,ProgressDialog progressDialog,GetEnquiryFormsResponseListener getEnquiryFormsResponseListener) {
        mContext=context;
        mProgressDialog=progressDialog;
        mGetEnquiryFormsResponseListener=getEnquiryFormsResponseListener;
    }

    public void callEnquiryFormApi(String userId) {
        if(!InternetConnection.isInternetConnected(mContext)) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            AppToast.showToast(mContext,mContext.getResources().getString(R.string.err_no_internet));
            return;
        }
        UserConnection userConnection= RetroFitAdapter.createService(UserConnection.class, ServerApi.SERVER_URL);
        Call<EnquiryFormsListResponseBean> call=userConnection.getEnquiryForms(userId);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<EnquiryFormsListResponseBean> call, Response<EnquiryFormsListResponseBean> response) {
        ArrayList<EnquiryFormsResponseBean> list=new ArrayList<>();
        if(response.isSuccessful()&& response.body().isSucess()) {
            list=response.body().getEnquiryFormsList();
            afterSuccessFullResult(true,list);
        }
        else {
            afterSuccessFullResult(false,list);
        }
    }

    @Override
    public void onFailure(Call<EnquiryFormsListResponseBean> call, Throwable t) {
        afterSuccessFullResult(false,new ArrayList<EnquiryFormsResponseBean>());
        AppToast.showToast(mContext,"Network error");
    }

    private void afterSuccessFullResult(boolean isSuccess, ArrayList<EnquiryFormsResponseBean> list) {
        mGetEnquiryFormsResponseListener.getEnquiryFormReponse(isSuccess,list);
    }

}