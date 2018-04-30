package com.autokartz.autokartz.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.interfaces.SuggestionOrderListener;
import com.autokartz.autokartz.utils.apiResponses.SuggestionResponseBean;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 3/6/2018.
 */

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.SuggestionHolder> {


    private Context mContext;
    private Activity mActivity;
    private ArrayList<SuggestionResponseBean> mSuggestionList;
    private SuggestionOrderListener mSuggestionOrderListener;


    public SuggestionAdapter(Context context, SuggestionOrderListener listener) {
        mContext = context;
        mSuggestionOrderListener = listener;
        mSuggestionList = new ArrayList<>();
    }

    @Override
    public SuggestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_suggestion, parent, false);
        return new SuggestionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SuggestionHolder holder, int position) {
        String suggestionProductName = mSuggestionList.get(position).getPartName();
        String suggestionBrand = mSuggestionList.get(position).getBrand();
        String suggestionPrice = "Rs." + mSuggestionList.get(position).getPrice();
        String suggestionTax = mSuggestionList.get(position).getTax() + "%";
        String suggestionCharges = "Rs." + mSuggestionList.get(position).getShipCharges();
        String suggestionAvailability = mSuggestionList.get(position).getAvailability();
        String suggestionWarranty = mSuggestionList.get(position).getWarranty();
        String suggestionPartUrl = mSuggestionList.get(position).getPartUrl();
        String suggestionShippingTime = mSuggestionList.get(position).getShipTime() + " " + "days";
        String suggestionQuality = mSuggestionList.get(position).getQuality();
        holder.mProductName.setText(suggestionProductName);
        holder.mBrandTv.setText(suggestionBrand);
        holder.mPriceTv.setText(suggestionPrice);
        holder.mTaxTv.setText(suggestionTax);
        holder.mShipChargeTv.setText(suggestionCharges);
        holder.mShipTimeTv.setText(suggestionShippingTime);
        if (suggestionPartUrl.matches("")) {
            holder.mpartUrlTextTv.setVisibility(View.GONE);
            holder.mpartUrlTv.setVisibility(View.GONE);
        } else {
            holder.mpartUrlTv.setText(suggestionPartUrl);
        }
        if (suggestionAvailability.matches("1")) {
            holder.mAvailTv.setText("Yes");
        } else {
            holder.mAvailTv.setText("No");
        }
        if (suggestionWarranty.matches("1")) {
            holder.mWarrantyTv.setText("Yes");
        } else {
            holder.mWarrantyTv.setText("No");
        }

        if (suggestionQuality.matches("1")) {
            holder.mQualityTv.setText("Genuine");
        }
        if (suggestionQuality.matches("2")) {
            holder.mQualityTv.setText("Performance");
        }
        if (suggestionQuality.matches("3")) {
            holder.mQualityTv.setText("Imported");
        }
        if (suggestionQuality.matches("4")) {
            holder.mQualityTv.setText("Refurbished");
        }
        if (suggestionQuality.matches("5")) {
            holder.mQualityTv.setText("After Market");
        }
        if (suggestionQuality.matches("6")) {
            holder.mQualityTv.setText("O.E. Replacement");
        }


    }

    @Override
    public int getItemCount() {
        return mSuggestionList.size();
    }

    public void setSuggestionList(ArrayList<SuggestionResponseBean> list) {
        mSuggestionList.clear();
        mSuggestionList.addAll(list);
    }

    public class SuggestionHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.suggestion_checkbox)
        CheckBox mCheckbox;
        @BindView(R.id.suggestion_product_name_value_tv)
        TextView mProductName;
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
        @BindView(R.id.parturl_valu_tv)
        TextView mpartUrlTv;
        @BindView(R.id.parturl_text_tv)
        TextView mpartUrlTextTv;


        public SuggestionHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.suggestion_checkbox})
        public void onCheckedClick() {
            int position = getAdapterPosition();
            if (mCheckbox.isChecked()) {
                mSuggestionOrderListener.updateOrderList(true, mSuggestionList.get(position));
            } else {
                mSuggestionOrderListener.updateOrderList(false, mSuggestionList.get(position));
            }
        }

        @OnClick({R.id.parturl_valu_tv})
        public void onpartUrlClick() {
            int position = getAdapterPosition();
            String URL = mSuggestionList.get(position).getPartUrl();
            Intent intentUrl = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
            mContext.startActivity(intentUrl);

        }
    }

}