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
import android.widget.Toast;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.dialoges.ProductDetailDialog;
import com.autokartz.autokartz.services.databases.preferences.AccountDetailHolder;
import com.autokartz.autokartz.utils.pojoClasses.CategoryInformation;
import com.autokartz.autokartz.utils.util.constants.AppConstantKeys;

import org.apache.poi.hssf.record.formula.functions.T;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 3/22/2018.
 */

public class EnquiryFormAddedPartAdapter extends RecyclerView.Adapter<EnquiryFormAddedPartAdapter.EnquiryFormAddedPartHolder> {

    private Context mContext;
    private Activity mActivity;
    private ArrayList<CategoryInformation> mAddedPartList;
    private AccountDetailHolder mAccountDetailHolder;
    private Button mSubmitButton;
    private Button mCarPartsBtn;
    EnquiryCarPartsDetailsAdapter mEnquiryCarPartsDetailsAdapter;

    public EnquiryFormAddedPartAdapter(Context context, Activity activity, Button mSubmitCarPartsBtn, Button mAddCarPartsBtn, EnquiryCarPartsDetailsAdapter enquiryCarPartsDetailsAdapter) {
        mContext = context;
        mActivity = activity;
        mAccountDetailHolder = new AccountDetailHolder(mContext);
        mSubmitButton = mSubmitCarPartsBtn;
        mEnquiryCarPartsDetailsAdapter = enquiryCarPartsDetailsAdapter;
        mCarPartsBtn = mAddCarPartsBtn;
        mAddedPartList = new ArrayList<>();
        mAddedPartList.clear();
        mAddedPartList.addAll(mAccountDetailHolder.getAddPartDetails());
    }

    @Override
    public EnquiryFormAddedPartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_enquiry_form_added_part_item, parent, false);
        return new EnquiryFormAddedPartHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EnquiryFormAddedPartHolder holder, int position) {
        holder.mPartNameTv.setText(mAddedPartList.get(position).getmPartName());
    }

    @Override
    public int getItemCount() {
        if (mAddedPartList.size() != 0) {
            mSubmitButton.setVisibility(View.VISIBLE);
            mCarPartsBtn.setBackgroundColor(mActivity.getResources().getColor(R.color.light_grey));
            mCarPartsBtn.setTextColor(mActivity.getResources().getColor(R.color.appcolorornage));
        }
        return mAddedPartList.size();
    }

    public class EnquiryFormAddedPartHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.enquiry_form_added_part_part_name_tv)
        TextView mPartNameTv;
        @BindView(R.id.enquiry_form_add_part_detail_iv)
        ImageView mDetailIv;
        @BindView(R.id.enquiry_form_add_part_delete_iv)
        ImageView mDeleteIv;

        public EnquiryFormAddedPartHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.enquiry_form_add_part_detail_iv})
        public void onClickDetailIv() {
            int position = getAdapterPosition();
            CategoryInformation catInfo = mAddedPartList.get(position);
            ProductDetailDialog productDetailDialog = new ProductDetailDialog(mContext, mActivity, AppConstantKeys.ADDED_PART, position);
            productDetailDialog.show();
        }

        @OnClick({R.id.enquiry_form_add_part_delete_iv})
        public void onClickDeleteIv() {
            int position = getAdapterPosition();
            mAddedPartList.remove(position);
            mEnquiryCarPartsDetailsAdapter.getItemCount();
            mAccountDetailHolder.setAddPart(mAddedPartList);
            notifyDataSetChanged();
        }

    }

}