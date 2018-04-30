package com.autokartz.autokartz.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.dialoges.ProductDetailDialog;
import com.autokartz.autokartz.services.databases.preferences.AccountDetailHolder;
import com.autokartz.autokartz.utils.pojoClasses.CategoryInformation;
import com.autokartz.autokartz.utils.util.AppToast;
import com.autokartz.autokartz.utils.util.constants.AppConstantKeys;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by user on 3/22/2018.
 */

public class CategoryCarAddPartAdapter extends RecyclerView.Adapter<CategoryCarAddPartAdapter.CategoryCarAddPartHolder> {

    private Context mContext;
    private Activity mActivity;
    private ArrayList<CategoryInformation> mAddedPartsList;
    private AccountDetailHolder mAccountDetailHolder;

    public CategoryCarAddPartAdapter(Context context, Activity activity, ArrayList<CategoryInformation> list) {
        mContext = context;
        mActivity = activity;
        mAccountDetailHolder = new AccountDetailHolder(mContext);
        mAddedPartsList = new ArrayList<>();
        mAddedPartsList.clear();
        mAddedPartsList.addAll(list);
    }

    @Override
    public CategoryCarAddPartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_add_part_item, parent, false);
        return new CategoryCarAddPartHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryCarAddPartHolder holder, int position) {
        holder.mAddPartEt.setText(mAddedPartsList.get(position).getmPartName());
    }

    @Override
    public int getItemCount() {
        if (mAddedPartsList.size() == 0) {
            mAddedPartsList.add(new CategoryInformation());
            mAccountDetailHolder.setAddPart(mAddedPartsList);
        }
        return mAddedPartsList.size();
    }

    public void addAddPart() {
        CategoryInformation catInfo = new CategoryInformation();
        //mAddedPartsList=mAccountDetailHolder.getAddPartDetails();
        catInfo = mAddedPartsList.get(mAddedPartsList.size() - 1);
        String partName = catInfo.getmPartName().trim();
        if (partName != null && !partName.isEmpty()) {
            CategoryInformation categoryInformation = new CategoryInformation();
            categoryInformation.setDefined(0);
            categoryInformation.setmPartId("0");
            categoryInformation.setmPartName("");
            mAddedPartsList.add(categoryInformation);
            mAccountDetailHolder.setAddPart(mAddedPartsList);
            notifyDataSetChanged();
        } else {
            AppToast.showToast(mContext, "Please enter Part Name");
        }
    }

    public ArrayList<CategoryInformation> updateAddedPartList() {
        CategoryInformation catInfo;
        //ArrayList<CategoryInformation> addPartList = new ArrayList<>();
        //addPartList = mAccountDetailHolder.getAddPartDetails();
        for (int i = 0; i < mAddedPartsList.size(); i++) {
            catInfo = mAddedPartsList.get(i);
            String partName = catInfo.getmPartName();
            if (partName == null || partName.isEmpty()) {
                mAddedPartsList.remove(i);
            }
        }
        return mAddedPartsList;
        //mAccountDetailHolder.setAddPart(mAddedPartsList);
    }

    public class CategoryCarAddPartHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.category_car_part_add_et)
        EditText mAddPartEt;
        @BindView(R.id.category_car_part_detail_iv)
        ImageView mAddPartDetailIv;
        @BindView(R.id.category_car_part_delete_iv)
        ImageView mAddPartDeleteIv;

        public CategoryCarAddPartHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.category_car_part_detail_iv})
        public void onClickAddPartDetailIv() {
            int position = getAdapterPosition();
            String partName = mAddPartEt.getText().toString().trim();
            if (partName != null && !partName.isEmpty()) {
                ArrayList<CategoryInformation> catAddPartList = mAccountDetailHolder.getAddPartDetails();
                CategoryInformation catInfo = catAddPartList.get(position);
                catInfo.setmPartName(partName);
                catAddPartList.set(position, catInfo);
                mAccountDetailHolder.setAddPart(catAddPartList);
                ProductDetailDialog productDetailDialog = new ProductDetailDialog(mContext, mActivity, AppConstantKeys.ADDED_PART, position);
                productDetailDialog.show();
            } else {
                AppToast.showToast(mContext, "Please enter Part Name");
            }
        }

        @OnTextChanged({R.id.category_car_part_add_et})
        public void onTextChanged() {
            String partName = mAddPartEt.getText().toString().trim();
            int position = getAdapterPosition();
            CategoryInformation catInfo;
            catInfo = mAddedPartsList.get(position);
            catInfo.setmPartName(partName);
            catInfo.setDefined(0);
            mAddedPartsList.set(position, catInfo);
            if (partName != null && !partName.isEmpty()) {
                mAccountDetailHolder.setAddPart(mAddedPartsList);
            }
        }

        @OnClick({R.id.category_car_part_delete_iv})
        public void onClickDeleteIv() {
            int position = getAdapterPosition();
            mAddedPartsList.remove(position);
            notifyDataSetChanged();
            mAccountDetailHolder.setAddPart(mAddedPartsList);
        }

    }

}