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
import com.autokartz.autokartz.services.databases.preferences.AccountDetailHolder;
import com.autokartz.autokartz.utils.apiResponses.EnquiryFormsResponseBean;
import com.autokartz.autokartz.utils.converter.ConvertDateFormat;
import com.autokartz.autokartz.utils.pojoClasses.UserNotificationCount;

import java.util.ArrayList;
import java.util.Collections;

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

    AccountDetailHolder mAccountHolder;
    private ArrayList<UserNotificationCount> mNotification;

    public EnquiryFormsAdapter(Context context, EnquiryFormsAdapterListener listener, ArrayList<UserNotificationCount> mUserNotificationCount, AccountDetailHolder mAccountDetailHolder) {
        mContext = context;
        mEnquiryFormsList = new ArrayList<>();
        this.mNotification = mUserNotificationCount;
        mEnquiryFormsAdapterListener = listener;
        this.mAccountHolder = mAccountDetailHolder;
    }

    @Override
    public EnquiryFormsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_enquiry, parent, false);
        return new EnquiryFormsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EnquiryFormsHolder holder, int position) {
        holder.enquiryText.setText(position + 1 + ".");
        String enquiryId = mEnquiryFormsList.get(position).getEnquiry_id();

        for (int i = 0; i < mNotification.size(); i++) {
            String notification_count = mNotification.get(i).getUserNotificationCount();
            String notificationCountEnquiryID = mNotification.get(i).getUserEnquiryId();
            if (enquiryId.matches(notificationCountEnquiryID)) {
                holder.mNotificationCount.setText(notification_count);
                holder.mNotificationCount.setVisibility(View.VISIBLE);
            }
        }

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
        Collections.reverse(mEnquiryFormsList);
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
        @BindView(R.id.notification_count_tv)
        TextView mNotificationCount;

        public EnquiryFormsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.item_enquiry_layout})
        public void onClickEnquiry() {
            int position = getAdapterPosition();
            String enquiryId = mEnquiryFormsList.get(position).getEnquiry_id();

            for (int i = 0; i < mNotification.size(); i++) {
                String notificationCountEnquiryID = mNotification.get(i).getUserEnquiryId();
                if (enquiryId.matches(notificationCountEnquiryID)) {
                    mNotification.remove(mNotification.get(i));
                    mAccountHolder.setNotificationCount(mNotification);

                }
            }

            mEnquiryFormsAdapterListener.getDataOfEnquirySelection(enquiryId);
        }

    }

}