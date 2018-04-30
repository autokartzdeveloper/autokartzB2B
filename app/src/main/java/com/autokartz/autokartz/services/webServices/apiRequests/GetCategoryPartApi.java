package com.autokartz.autokartz.services.webServices.apiRequests;

import android.app.ProgressDialog;
import android.content.Context;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.interfaces.GetCategoryPartListener;
import com.autokartz.autokartz.services.connectionClasses.UserConnection;
import com.autokartz.autokartz.utils.apiResponses.CategoryPartListResultBean;
import com.autokartz.autokartz.utils.apiResponses.CategoryPartResultBean;
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
 * Created by user on 2/21/2018.
 */

public class GetCategoryPartApi implements Callback<CategoryPartListResultBean> {

    private Context mContext;
    private static final String TAG = GetCategoryPartApi.class.getName();
    private ProgressDialog mProgressDialog;
    private GetCategoryPartListener mGetCategoryPartListener;


    public GetCategoryPartApi(Context context, GetCategoryPartListener getCategoryPartListener, ProgressDialog progressDialog) {
        mContext = context;
        mProgressDialog = progressDialog;
        mGetCategoryPartListener = getCategoryPartListener;
    }

    public void callCategoryApi() {
        if (!InternetConnection.isInternetConnected(mContext)) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            AppToast.showToast(mContext, mContext.getResources().getString(R.string.err_no_internet));
            return;
        }
        UserConnection userConnection = RetroFitAdapter.createService(UserConnection.class, ServerApi.SERVER_URL);
        Call<CategoryPartListResultBean> call = userConnection.getCategoryPart();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<CategoryPartListResultBean> call, Response<CategoryPartListResultBean> response) {
        if (response.isSuccessful() && response.body().isSuccess()) {
            ArrayList<CategoryPartResultBean> list = response.body().getCatPartsList();
            if (list != null) {
                afterSuccessfullResponse(true, list);
                //AppToast.showToast(mContext,response.body());
            }
        } else {
            AppToast.showToast(mContext, "Response Failure");
            afterSuccessfullResponse(false, null);
        }
    }

    @Override
    public void onFailure(Call<CategoryPartListResultBean> call, Throwable t) {
        Logger.LogError(TAG, t.getMessage());
        DismissDialog.dismissWithCheck(mProgressDialog);
        AppToast.showToast(mContext, "Network Error");
        afterSuccessfullResponse(false, null);
    }

    private void afterSuccessfullResponse(boolean isReceived, ArrayList<CategoryPartResultBean> catPartList) {
        DismissDialog.dismissWithCheck(mProgressDialog);
        mGetCategoryPartListener.getCatPartList(isReceived, catPartList);

    }

}