package com.autokartz.autokartz.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.interfaces.GetCarPartsListener;
import com.autokartz.autokartz.services.databases.LocalDatabase.DatabaseCURDOperations;
import com.autokartz.autokartz.utils.pojoClasses.CatItem;
import com.autokartz.autokartz.utils.pojoClasses.CategoryInformation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Cortana on 1/13/2018.
 */

public class SubCategoryPartAdapter extends RecyclerView.Adapter<SubCategoryPartAdapter.SubCategoryPartHolder> {
    private Context mContext;
    private Activity mActivity;
    private ArrayList<CatItem> mSubCatList;
    private DatabaseCURDOperations databaseCURDOperations;
    private CategoryInformation mCategoryInformation;
    private GetCarPartsListener mGetCarPartsListener;

    public SubCategoryPartAdapter(Context context, Activity activity, GetCarPartsListener getCarPartsListener) {
        mContext = context;
        mSubCatList = new ArrayList<>();
        mActivity = activity;
        mGetCarPartsListener = getCarPartsListener;
        databaseCURDOperations = new DatabaseCURDOperations(mContext);
    }

    @Override
    public SubCategoryPartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_category_item_layout, parent, false);
        return new SubCategoryPartHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SubCategoryPartHolder holder, int position) {
        holder.catNameTv.setText(mSubCatList.get(position).getName());
        holder.setIsRecyclable(false);
        holder.dropDownIv.setImageResource(R.drawable.ic_plus);
        holder.id = mSubCatList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return mSubCatList.size();
    }

    public void setSubCatList(ArrayList<CatItem> subCatList) {
        mSubCatList.clear();
        mSubCatList.addAll(subCatList);
    }

    public void setCatInfo(CategoryInformation categoryInformation) {
        mCategoryInformation = categoryInformation;
    }

    public class SubCategoryPartHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.cat_name_tv)
        TextView catNameTv;
        @BindView(R.id.drop_down_iv)
        ImageView dropDownIv;
        @BindView(R.id.sub_cat_rv)
        RecyclerView subCatRv;
        @BindView(R.id.item_rel_layout)
        RelativeLayout itemLayout;
        PartsAdapter partsAdapter;
        ArrayList<CatItem> partsList;
        boolean isOpen = false;
        String id;

        public SubCategoryPartHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemLayout.setBackgroundResource(R.color.LightOrange);
            partsAdapter = new PartsAdapter(mContext, mActivity, mGetCarPartsListener);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
            subCatRv.setLayoutManager(layoutManager);
            subCatRv.setItemAnimator(new DefaultItemAnimator());
            subCatRv.setAdapter(partsAdapter);
        }

        @OnClick({R.id.cat_name_tv, R.id.drop_down_iv, R.id.item_rel_layout})
        public void onClickSubCat() {
            if (isOpen) {
                dropDownIv.setImageResource(R.drawable.ic_plus);
                subCatRv.setVisibility(View.GONE);
                isOpen = false;
            } else {
                dropDownIv.setImageResource(R.drawable.ic_minus);
                subCatRv.setVisibility(View.VISIBLE);
                isOpen = true;
                String subCatName = catNameTv.getText().toString();
                mCategoryInformation.setmSubCatName(subCatName);
                mCategoryInformation.setmSubCatId(id);
                partsList = databaseCURDOperations.getCarParts(id);
                partsAdapter.setPartsList(partsList);
                partsAdapter.setCatInfo(mCategoryInformation);
                partsAdapter.notifyDataSetChanged();
            }
        }

    }

}