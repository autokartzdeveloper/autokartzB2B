package com.autokartz.autokartz.services.webServices.apiRequests;

import android.app.ProgressDialog;
import android.content.Context;
import com.autokartz.autokartz.R;
import com.autokartz.autokartz.interfaces.LoginResponseListener;
import com.autokartz.autokartz.services.connectionClasses.UserConnection;
import com.autokartz.autokartz.utils.apiRequestBeans.SignInBean;
import com.autokartz.autokartz.utils.apiResponses.UserDetailBean;
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

public class ManualLoginApi implements Callback<UserDetailBeanResult> {


    private Context mContext;
    private LoginResponseListener mLoginResponseListener;
    private static final String TAG=ManualLoginApi.class.getName();
    private ProgressDialog mProgressDialog;

    public ManualLoginApi(Context context,LoginResponseListener loginResponseListener,ProgressDialog progressDialog) {
        mContext=context;
        mLoginResponseListener=loginResponseListener;
        mProgressDialog=progressDialog;
    }

    public void callLoginApi(String emailId, String password) {
        SignInBean signInBean=new SignInBean(emailId,password);
        if(!InternetConnection.isInternetConnected(mContext)) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            AppToast.showToast(mContext,mContext.getResources().getString(R.string.err_no_internet));
            return;
        }
        UserConnection userConnection= RetroFitAdapter.createService(UserConnection.class, ServerApi.SERVER_URL);
        Call<UserDetailBeanResult> call=userConnection.userSignIn(signInBean.getEmail(),signInBean.getPassword());
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<UserDetailBeanResult> call, Response<UserDetailBeanResult> response) {
        UserDetailBean userDetailBean=new UserDetailBean();
        if(response.isSuccessful()&&response.body().isSuccess()) {
            userDetailBean=response.body().getUserDetailBean();
            AppToast.showToast(mContext,response.body().getMessage());
            afterSuccessfullResponse(true,userDetailBean);
        } else {
            AppToast.showToast(mContext,response.body().getMessage());
            afterSuccessfullResponse(false,userDetailBean);
        }
    }

    @Override
    public void onFailure(Call<UserDetailBeanResult> call, Throwable t) {
        Logger.LogError(TAG,t.getMessage());
        DismissDialog.dismissWithCheck(mProgressDialog);
        AppToast.showToast(mContext,"Network Error");
    }

    private void afterSuccessfullResponse(boolean status,UserDetailBean userDetailBean) {
        mLoginResponseListener.getLoginResponse(status,userDetailBean);
    }

}