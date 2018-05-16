package com.autokartz.autokartz.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.interfaces.EnquiryFormsAdapterListener;
import com.autokartz.autokartz.utils.apiResponses.EnquiryFormsResponseBean;
import com.autokartz.autokartz.utils.converter.ConvertDateFormat;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 2/26/2018.
 */

public class EnquiryFormsAdapter extends RecyclerView.Adapter<EnquiryFormsAdapter.EnquiryFormsHolder> {
    private Context mContext;
    private ArrayList<EnquiryFormsResponseBean> mEnquiryFormsList;
    private ProgressDialog mProgressDialog;
    private EnquiryFormsAdapterListener mEnquiryFormsAdapterListener;

    public EnquiryFormsAdapter(Context context, EnquiryFormsAdapterListener listener) {
        mContext = context;
        mEnquiryFormsList = new ArrayList<>();
        mEnquiryFormsAdapterListener = listener;
    }

    @Override
    public EnquiryFormsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_enquiry, parent, false);
        return new EnquiryFormsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EnquiryFormsHolder holder, int position) {
        holder.enquiryText.setText(position + 1 + ".");
        String date = mEnquiryFormsList.get(position).getCreatedAt();
        holder.dateTv.setText(ConvertDateFormat.convertDateFormat(date));
        holder.mEnquiryBrand.setText(mEnquiryFormsList.get(position).getBrand());
    }

    @Override
    public int getItemCount() {
        return mEnquiryFormsList.size();
    }

    public void setEnquiryFormsList(ArrayList<EnquiryFormsResponseBean> enquiryFormList) {
        mEnquiryFormsList.clear();
        mEnquiryFormsList.addAll(enquiryFormList);
    }

    public class EnquiryFormsHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.enquiry_form_text)
        TextView enquiryText;
        @BindView(R.id.enquiry_form_date)
        TextView dateTv;
        @BindView(R.id.item_enquiry_layout)
        LinearLayout mLayout;
        @BindView(R.id.enquiry_form_brand)
        TextView mEnquiryBrand;

        public EnquiryFormsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.item_enquiry_layout})
        public void onClickEnquiry() {
            int position = getAdapterPosition();
            String enquiryId = mEnquiryFormsList.get(position).getEnquiry_id();
            mEnquiryFormsAdapterListener.getDataOfEnquirySelection(enquiryId);
        }

    }

}