package com.autokartz.autokartz.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.activities.MainDashboardActivity;
import com.autokartz.autokartz.fragments.DisputeFragment;
import com.autokartz.autokartz.utils.apiResponses.SuggestionResponseBean;
import com.autokartz.autokartz.utils.converter.ConvertDateFormat;
import com.autokartz.autokartz.utils.pojoClasses.OrderDetail;
import com.autokartz.autokartz.utils.util.constants.IntentKeyConstants;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 3/22/2018.
 */

public class OrderDetailItemAdapter extends RecyclerView.Adapter<OrderDetailItemAdapter.OrderDetailItemHolder> {


    private static final String CURRENT_TAG = OrderDetailItemAdapter.class.getName();
    private Context mContext;
    String suggestion_id;
    int closingDispute;
    private Activity mActivity;
    private OrderDetail mOrderDetail;
    private ArrayList<SuggestionResponseBean> mSuggestionResponseBeanList;


    public OrderDetailItemAdapter(Context context, Activity activity, ArrayList<SuggestionResponseBean> list, OrderDetail mOrderDetail, int disputeClosingDay) {
        mContext = context;
        this.closingDispute = disputeClosingDay;
        mActivity = activity;
        this.mOrderDetail = mOrderDetail;
        mSuggestionResponseBeanList = new ArrayList<>();
        mSuggestionResponseBeanList.clear();
        mSuggestionResponseBeanList.addAll(list);
    }

    @Override
    public OrderDetailItemAdapter.OrderDetailItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_order_detail_items, parent, false);

        return new OrderDetailItemHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final OrderDetailItemAdapter.OrderDetailItemHolder holder, int position) {
        suggestion_id = mSuggestionResponseBeanList.get(position).getId();
        holder.mProductNameTv.setText(mSuggestionResponseBeanList.get(position).getPartName());
        holder.mBrandTv.setText(mSuggestionResponseBeanList.get(position).getBrand());
        holder.mPriceTv.setText(mContext.getResources().getString(R.string.Rs) + mSuggestionResponseBeanList.get(position).getPrice());
        holder.mTaxTv.setText(mSuggestionResponseBeanList.get(position).getTax() + "%");
        holder.mShipChargeTv.setText(mContext.getResources().getString(R.string.Rs) + mSuggestionResponseBeanList.get(position).getShipCharges());
        holder.mShipTimeTv.setText(mSuggestionResponseBeanList.get(position).getShipTime() + " " + "days");
        if (mSuggestionResponseBeanList.get(position).getAvailability().matches("1")) {
            holder.mAvailTv.setText("Yes");
        } else {
            holder.mAvailTv.setText("No");
        }
        if (mSuggestionResponseBeanList.get(position).getWarranty().matches("1")) {
            holder.mWarrantyTv.setText("Yes");
        } else {
            holder.mWarrantyTv.setText("No");
        }
        if (mSuggestionResponseBeanList.get(position).getQuality().matches("1")) {
            holder.mQualityTv.setText("Genuine");
        }
        if (mSuggestionResponseBeanList.get(position).getQuality().matches("2")) {
            holder.mQualityTv.setText("Performance");
        }
        if (mSuggestionResponseBeanList.get(position).getQuality().matches("3")) {
            holder.mQualityTv.setText("Imported");
        }
        if (mSuggestionResponseBeanList.get(position).getQuality().matches("4")) {
            holder.mQualityTv.setText("Refurbished");
        }
        if (mSuggestionResponseBeanList.get(position).getQuality().matches("5")) {
            holder.mQualityTv.setText("After Market");
        }
        if (mSuggestionResponseBeanList.get(position).getQuality().matches("6")) {
            holder.mQualityTv.setText("O.E. Replacement");
        }
        if (mOrderDetail.getStatus().matches("2")) {
            holder.mDisputeBtn.setVisibility(View.GONE);
        }

        if (closingDispute == 1) {
            Toast.makeText(mContext, "asd", Toast.LENGTH_SHORT).show();
            holder.mDisputeBtn.setVisibility(View.GONE);
        } else {
            Toast.makeText(mContext, "asd", Toast.LENGTH_SHORT).show();
        }
        if (mOrderDetail.getDeliveryStatus() != null && mOrderDetail.getDeliveryStatus().matches("3")) {
            holder.mDisputeBtn.setVisibility(View.VISIBLE);
        } else {
            holder.mDisputeBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mSuggestionResponseBeanList.size();
    }

    public class OrderDetailItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.suggestion_product_name_value_tv)
        TextView mProductNameTv;
        @BindView(R.id.suggestion_brand_val_tv)
        TextView mBrandTv;
        @BindView(R.id.suggestion_price_val_tv)
        TextView mPriceTv;
        @BindView(R.id.suggestion_tax_val_tv)
        TextView mTaxTv;
        @BindView(R.id.suggestion_ship_charge_val_tv)
        TextView mShipChargeTv;
        @BindView(R.id.suggestion_ship_time_val_tv)
        TextView mShipTimeTv;
        @BindView(R.id.suggestion_avail_val_tv)
        TextView mAvailTv;
        @BindView(R.id.suggestion_quality_val_tv)
        TextView mQualityTv;
        @BindView(R.id.suggestion_warranty_val_tv)
        TextView mWarrantyTv;
        @BindView(R.id.order_detail_item_dispute_btn)
        Button mDisputeBtn;
        boolean isOpenedForDispute = true;

        public OrderDetailItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @OnClick({R.id.order_detail_item_dispute_btn})
        public void onClickDisputeBtn() {
            if (isOpenedForDispute) {
                String Time = mOrderDetail.getDeliveryUpdated();
                String deliveryTime = ConvertDateFormat.convertDateFormat(Time);
                Fragment fragment = new DisputeFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(IntentKeyConstants.KEY_, mOrderDetail);
                bundle.putString("suggestion_id", suggestion_id);
                fragment.setArguments(bundle);
                MainDashboardActivity activity = (MainDashboardActivity) mActivity;
                FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, fragment, CURRENT_TAG);
                fragmentTransaction.addToBackStack(CURRENT_TAG);
                fragmentTransaction.commit();
            }
        }

    }

}