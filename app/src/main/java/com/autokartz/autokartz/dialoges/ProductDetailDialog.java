package com.autokartz.autokartz.dialoges;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.EditText;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.activities.MainDashboardActivity;
import com.autokartz.autokartz.adapters.ProductImageAdapter;
import com.autokartz.autokartz.interfaces.GetImageListener;
import com.autokartz.autokartz.services.databases.preferences.AccountDetailHolder;
import com.autokartz.autokartz.utils.pojoClasses.CategoryInformation;
import com.autokartz.autokartz.utils.util.constants.AppConstantKeys;
import com.autokartz.autokartz.utils.util.dialogs.ShowDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Cortana on 1/17/2018.
 */

public class ProductDetailDialog extends Dialog {


    @BindView(R.id.product_detail_et)
    EditText mProductDetailEt;
    @BindView(R.id.product_images_rv)
    RecyclerView mProductDetailImagesRv;
    private Activity mActivity;
    private Context mContext;
    @BindView(R.id.submit_product_detail_btn)
    Button mProductDetailSubmitBtn;
    private static GetImageListener mGetImageListener;
    private static ProductImageAdapter productImageAdapter;
    private static ArrayList<String> mImageList;
    private CategoryInformation mCategoryInformation;
    private static int currentPosition = -1;
    private int cPosition = -1;
    private String LISTTAG = "";
    private AccountDetailHolder mAccountDetailHolder;
    private ProgressDialog mProgressDialog;

    public ProductDetailDialog(@NonNull Context context, Activity activity, String TAG, int position) {
        super(activity);
        mActivity = activity;
        mContext = context;
        init();
        LISTTAG = TAG;
        currentPosition = position;
        cPosition = position;
        if (LISTTAG.equalsIgnoreCase(AppConstantKeys.SELECTED_PART)) {
            mCategoryInformation = mAccountDetailHolder.getSelectedCarParts().get(currentPosition);
        } else if (LISTTAG.equalsIgnoreCase(AppConstantKeys.ADDED_PART)) {
            mCategoryInformation = mAccountDetailHolder.getAddPartDetails().get(currentPosition);
        }
        mImageList = mCategoryInformation.getmImagePathList();
    }

    private void init() {
        initVariables();
    }

    private void initVariables() {
        mAccountDetailHolder = new AccountDetailHolder(mContext);
        mGetImageListener = (MainDashboardActivity) mActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_popup_layout);
        ButterKnife.bind(this);
        setCancelable(false);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        mActivity.getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        int height = (int) (displayMetrics.heightPixels * 0.75);
        int width = (int) (displayMetrics.widthPixels * 0.9);
        getWindow().setLayout(width, height);
        initiateRecyclerView();
        mProductDetailEt.setText(mCategoryInformation.getmProductDetail());
    }

    private void initiateRecyclerView() {
        productImageAdapter = new ProductImageAdapter(mActivity);
        productImageAdapter.setImageList(mImageList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        mProductDetailImagesRv.setLayoutManager(layoutManager);
        mProductDetailImagesRv.setItemAnimator(new DefaultItemAnimator());
        mProductDetailImagesRv.setAdapter(productImageAdapter);
    }

    @OnClick({R.id.submit_product_detail_btn})
    public void onClickSubmit() {
        mImageList = productImageAdapter.getImageList();
        String detail = mProductDetailEt.getText().toString().trim();
        mCategoryInformation.setmProductDetail(detail);
        mCategoryInformation.setmImagePathList(mImageList);
        if (LISTTAG.equalsIgnoreCase(AppConstantKeys.SELECTED_PART)) {
            ArrayList<CategoryInformation> list = mAccountDetailHolder.getSelectedCarParts();
            list.set(cPosition, mCategoryInformation);
            mAccountDetailHolder.setSelectedCarParts(list);
        } else if (LISTTAG.equalsIgnoreCase(AppConstantKeys.ADDED_PART)) {
            ArrayList<CategoryInformation> list = mAccountDetailHolder.getAddPartDetails();
            list.set(cPosition, mCategoryInformation);
            mAccountDetailHolder.setAddPart(list);
        }
        dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }

    public static class CustomBroadCastReceiver extends BroadcastReceiver {

        public CustomBroadCastReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String path = mGetImageListener.getImage(currentPosition);
            mImageList.clear();
            mImageList.addAll(productImageAdapter.getImageList());
            mImageList.add(path);
            productImageAdapter.setImageList(mImageList);
            productImageAdapter.notifyDataSetChanged();
        }

    }

}