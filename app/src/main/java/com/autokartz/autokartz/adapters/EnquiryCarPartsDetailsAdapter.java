package com.autokartz.autokartz.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.dialoges.ProductDetailDialog;
import com.autokartz.autokartz.services.databases.preferences.AccountDetailHolder;
import com.autokartz.autokartz.utils.pojoClasses.CarInformation;
import com.autokartz.autokartz.utils.pojoClasses.CategoryInformation;
import com.autokartz.autokartz.utils.util.constants.AppConstantKeys;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Cortana on 1/16/2018.
 */

public class EnquiryCarPartsDetailsAdapter extends RecyclerView.Adapter<EnquiryCarPartsDetailsAdapter.EnquiryCarPartsDetailsHolder> {

    private Activity mActivity;
    private Button mSubmitCarBtn;
    private Context mContext;
    private AccountDetailHolder mAccountDetailHolder;
    private ArrayList<CategoryInformation> selectedCarPartList;
    private Button mCustomerSubmitBtn;
    private Button mSelfSubmitBtn;
    private Button mCarPartsBtn;
    CarInformation mCarInfo;

    public EnquiryCarPartsDetailsAdapter(Activity context, CarInformation carInfo, Button mSubmitForSelfBtn, Button mSubmitForCustomerBtn, Button mSubmitCarPartsBtn, Button mAddCarPartsBtn) {
        mActivity = context;
        mCarPartsBtn = mAddCarPartsBtn;
        mSubmitCarBtn = mSubmitCarPartsBtn;
        mCustomerSubmitBtn = mSubmitForCustomerBtn;
        mSelfSubmitBtn = mSubmitForSelfBtn;
        mCarInfo = carInfo;
        mAccountDetailHolder = new AccountDetailHolder(mActivity);
        selectedCarPartList = mAccountDetailHolder.getSelectedCarParts();
    }

    @Override
    public EnquiryCarPartsDetailsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_part_layout, parent, false);
        return new EnquiryCarPartsDetailsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EnquiryCarPartsDetailsHolder holder, int position) {
        holder.categoryInformation = selectedCarPartList.get(position);
        holder.itemNameTv.setText(holder.categoryInformation.getmPartName());
        holder.mImageView.setImageResource(holder.categoryInformation.getImageResId());
    }

    @Override
    public int getItemCount() {
        selectedCarPartList.size();
        if (selectedCarPartList.size() != 0) {
            mSubmitCarBtn.setVisibility(View.GONE);
            mSelfSubmitBtn.setVisibility(View.VISIBLE);
            mCustomerSubmitBtn.setVisibility(View.VISIBLE);
            mCarPartsBtn.setBackgroundColor(mActivity.getResources().getColor(R.color.light_grey));
            mCarPartsBtn.setTextColor(mActivity.getResources().getColor(R.color.appcolorornage));
        }
        if (selectedCarPartList.size() != 0 && mCarInfo.getmType().matches("0") || mCarInfo.getmType().matches("1")) {
            mSubmitCarBtn.setVisibility(View.VISIBLE);
            mSelfSubmitBtn.setVisibility(View.GONE);
            mCustomerSubmitBtn.setVisibility(View.GONE);
            mCarPartsBtn.setBackgroundColor(mActivity.getResources().getColor(R.color.light_grey));
            mCarPartsBtn.setTextColor(mActivity.getResources().getColor(R.color.appcolorornage));
        }

        return selectedCarPartList.size();
    }

    public void setSelectedCarPartList(ArrayList<CategoryInformation> list) {
        selectedCarPartList.clear();
        selectedCarPartList.addAll(list);
    }

    public class EnquiryCarPartsDetailsHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.item_name_tv)
        TextView itemNameTv;
        @BindView(R.id.item_detail_iv)
        ImageView itemDetailIv;
        @BindView(R.id.item_cancel_iv)
        ImageView itemCancelIv;
        @BindView(R.id.selected_car_part_iv)
        ImageView mImageView;
        CategoryInformation categoryInformation;

        public EnquiryCarPartsDetailsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            categoryInformation = new CategoryInformation();
        }

        @OnClick({R.id.item_cancel_iv})
        public void onClickCancel() {
            for (int i = 0; i < selectedCarPartList.size(); i++) {
                if (selectedCarPartList.get(i).equals(categoryInformation)) {
                    selectedCarPartList.remove(i);
                    notifyDataSetChanged();
                    mAccountDetailHolder.setSelectedCarParts(selectedCarPartList);
                }
            }
        }

        @OnClick({R.id.item_detail_iv})
        public void onClickDetail() {
            int position = getAdapterPosition();
            ProductDetailDialog dialog = new ProductDetailDialog(mContext, mActivity, AppConstantKeys.SELECTED_PART, position);
            dialog.show();
            //todo open dialogbox and display the details.
        }

    }

}