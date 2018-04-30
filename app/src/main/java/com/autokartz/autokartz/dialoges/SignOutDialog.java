package com.autokartz.autokartz.dialoges;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;
import com.autokartz.autokartz.R;
import com.autokartz.autokartz.interfaces.SignOutResponseListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by user on 2/3/2018.
 */

public class SignOutDialog extends Dialog {


    @BindView(R.id.signout_yes_tv)
    TextView mYesTv;
    @BindView(R.id.signout_no_tv)
    TextView mNoTv;
    private Context mContext;
    private SignOutResponseListener mSignOutResponseListener;

    public SignOutDialog(@NonNull Context context,SignOutResponseListener signOutResponseListener) {
        super(context);
        mContext=context;
        mSignOutResponseListener=signOutResponseListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_signout);
        ButterKnife.bind(this);
        setCancelable(false);
    }

    @OnClick({R.id.signout_yes_tv})
    public void onClickYesTv() {
        mSignOutResponseListener.signoutResponse(true);
        dismiss();
    }

    @OnClick({R.id.signout_no_tv})
    public void onClickNoTv() {
        mSignOutResponseListener.signoutResponse(false);
        dismiss();
    }

    @OnTouch({R.id.signout_yes_tv})
    public boolean onTouchYesTv() {
        mYesTv.setBackgroundColor(mContext.getResources().getColor(R.color.LightGray));
        mSignOutResponseListener.signoutResponse(true);
        dismiss();
        return true;
    }

    @OnTouch({R.id.signout_no_tv})
    public boolean onTouchNoTv() {
        mNoTv.setBackgroundColor(mContext.getResources().getColor(R.color.LightGray));
        mSignOutResponseListener.signoutResponse(false);
        dismiss();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }

}