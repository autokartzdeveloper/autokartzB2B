package com.autokartz.autokartz.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.interfaces.OrderAdapterListener;
import com.autokartz.autokartz.utils.apiResponses.OrderIdResponse;
import com.autokartz.autokartz.utils.converter.ConvertDateFormat;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Cortana on 3/14/2018.
 */

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersHolder> {

    private Context mContext;
    private Activity mActivity;
    private ArrayList<OrderIdResponse> mOrderIdList;
    private OrderAdapterListener mOrderAdapterListener;
    private static final String CURRENT_TAG = OrdersAdapter.class.getName();

    public OrdersAdapter(Context context, OrderAdapterListener listener, ArrayList<OrderIdResponse> list) {
        mContext = context;
        mOrderIdList = new ArrayList<>();
        mOrderAdapterListener = listener;
        mOrderIdList.clear();
        mOrderIdList.addAll(list);
    }

    @Override
    public OrdersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_order_item, parent, false);
        return new OrdersHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrdersHolder holder, int position) {
        if (mOrderIdList.get(position).getStatus().equalsIgnoreCase("1")) {
            holder.mLayout.setBackgroundColor(mContext.getResources().getColor(R.color.LightGray));
        } else {
        }
        holder.mOrderIdTv.setText(position + 1 + ".");

        String date = mOrderIdList.get(position).getDate();
        String date1 = ConvertDateFormat.convertDateFormat(date);

        holder.mDateTv.setText(date1);
        holder.mAmountTv.setText(mContext.getResources().getString(R.string.Rs) + mOrderIdList.get(position).getAmount());
        if (mOrderIdList.get(position).getStatus().matches("1")) {
            holder.mOrderStatus.setText("Success");
            holder.mOrderStatus.setTextColor(mContext.getResources().getColor(R.color.primary_green));
        } else if (mOrderIdList.get(position).getStatus().matches("2")) {
            holder.mOrderStatus.setText("Cancelled");
            holder.mOrderStatus.setTextColor(mContext.getResources().getColor(R.color.cb_errorRed));

        } else {
            holder.mOrderStatus.setText("Failed");
            holder.mOrderStatus.setTextColor(mContext.getResources().getColor(R.color.cb_errorRed));

        }

    }

    @Override
    public int getItemCount() {
        return mOrderIdList.size();
    }

    public void setOrderIdList(ArrayList<OrderIdResponse> list) {
        mOrderIdList.clear();
        mOrderIdList.addAll(list);
    }

    public class OrdersHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.order_item_orderid_tv)
        TextView mOrderIdTv;
        @BindView(R.id.order_item_status_tv)
        TextView mOrderStatus;
        @BindView(R.id.order_item_date_tv)
        TextView mDateTv;
        @BindView(R.id.order_item_amount_tv)
        TextView mAmountTv;
        @BindView(R.id.order_id_layout)
        RelativeLayout mLayout;


        public OrdersHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.order_id_layout})
        public void onClickOrderId() {
            int position = getAdapterPosition();
            String orderId = mOrderIdList.get(position).getOrderId();
            mOrderAdapterListener.getOrderIdSelected(orderId);
        }

    }

}