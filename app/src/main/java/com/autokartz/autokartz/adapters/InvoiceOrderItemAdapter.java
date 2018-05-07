package com.autokartz.autokartz.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.utils.apiResponses.SuggestionResponseBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 3/10/2018.
 */

public class InvoiceOrderItemAdapter extends RecyclerView.Adapter<InvoiceOrderItemAdapter.InvoiceOrderItemHolder> {


    private Context mContext;
    private ArrayList<SuggestionResponseBean> mOrderList;

    public InvoiceOrderItemAdapter(Context context, ArrayList<SuggestionResponseBean> list) {
        mContext = context;
        mOrderList = new ArrayList<>();
        mOrderList.clear();
        mOrderList.addAll(list);
    }

    @Override
    public InvoiceOrderItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_invoice_order_item, parent, false);
        return new InvoiceOrderItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(InvoiceOrderItemHolder holder, int position) {
        holder.mNameTv.setText(mOrderList.get(position).getPartName());
        holder.mAmountTv.setText(mContext.getResources().getString(R.string.Rs) + mOrderList.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }

    public class InvoiceOrderItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.invoice_order_item_name_tv)
        TextView mNameTv;
        @BindView(R.id.invoice_order_item_amount_tv)
        TextView mAmountTv;

        public InvoiceOrderItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}