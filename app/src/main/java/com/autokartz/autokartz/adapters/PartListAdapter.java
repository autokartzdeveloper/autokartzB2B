package com.autokartz.autokartz.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.interfaces.SuggestionOrderListener;
import com.autokartz.autokartz.utils.apiResponses.EnquiryPartSuggestionResponseBean;
import com.autokartz.autokartz.utils.apiResponses.SuggestionResponseBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 3/1/2018.
 */

public class PartListAdapter extends RecyclerView.Adapter<PartListAdapter.PartListHolder> implements SuggestionOrderListener {


    private Context mContext;
    private Activity mActivity;

    private ArrayList<EnquiryPartSuggestionResponseBean> mEnquiryPartSuggestionList;
    private ArrayList<SuggestionResponseBean> mOrderList;

    public PartListAdapter(Context context) {
        mContext = context;
        mEnquiryPartSuggestionList = new ArrayList<>();
        mOrderList = new ArrayList<>();
    }

    @Override
    public PartListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_part_list, parent, false);
        return new PartListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PartListHolder holder, int position) {
        holder.partNameTv.setText(mEnquiryPartSuggestionList.get(position).getPartName());
        holder.mAgentRemarkTv.setText(mEnquiryPartSuggestionList.get(position).getAgentRemarks());
        holder.suggestionList.clear();
        holder.mNoTextTv.setVisibility(View.VISIBLE);
        holder.mPartRv.setVisibility(View.GONE);
        int size = mEnquiryPartSuggestionList.get(position).getSuggestionList().size();
        if (size > 0) {
            holder.mNoTextTv.setVisibility(View.GONE);
            holder.mPartRv.setVisibility(View.VISIBLE);
            holder.mAgentRemark.setVisibility(View.VISIBLE);
            holder.mAgentRemarkTv.setVisibility(View.VISIBLE);
            holder.suggestionList.addAll(mEnquiryPartSuggestionList.get(position).getSuggestionList());
            holder.suggestionAdapter.setSuggestionList(holder.suggestionList);
            holder.suggestionAdapter.notifyDataSetChanged();

        } else {
            holder.mNoTextTv.setVisibility(View.VISIBLE);
            holder.mPartRv.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
        return mEnquiryPartSuggestionList.size();
    }

    public ArrayList<SuggestionResponseBean> getOrderList() {
        return mOrderList;
    }

    public void setmEnquiryFormsList(ArrayList<EnquiryPartSuggestionResponseBean> list) {
        mEnquiryPartSuggestionList.clear();
        mEnquiryPartSuggestionList.addAll(list);
    }

    @Override
    public void updateOrderList(boolean isAdded, SuggestionResponseBean suggestion) {
        if (isAdded) {
            mOrderList.add(suggestion);
        } else {
            int size = mOrderList.size();
            SuggestionResponseBean suggestionResponse;
            for (int i = 0; i < size; i++) {
                suggestionResponse = mOrderList.get(i);
                if (suggestionResponse.equals(suggestion)) {
                    mOrderList.remove(i);
                    break;
                }
            }
        }
    }

    public class PartListHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.part_name_list_tv)
        TextView partNameTv;
        @BindView(R.id.part_list_rv)
        RecyclerView mPartRv;
        @BindView(R.id.suggestion_item_no_text_tv)
        TextView mNoTextTv;
        @BindView(R.id.agent_remarkval_tv)
        TextView mAgentRemarkTv;

        @BindView(R.id.agent_remark_tv)
        TextView mAgentRemark;
        SuggestionAdapter suggestionAdapter;
        private ArrayList<SuggestionResponseBean> suggestionList;
        private ArrayList<SuggestionResponseBean> orderList;

        public PartListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setRecyclerView();
            init();
        }

        private void init() {
            suggestionList = new ArrayList<>();
            orderList = new ArrayList<>();
        }

        private void setRecyclerView() {
            suggestionAdapter = new SuggestionAdapter(mContext, PartListAdapter.this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
            mPartRv.setLayoutManager(layoutManager);
            mPartRv.setItemAnimator(new DefaultItemAnimator());
            mPartRv.setAdapter(suggestionAdapter);
        }

        @OnClick({R.id.part_name_list_tv})
        public void onClickPartName() {
        }

    }

}