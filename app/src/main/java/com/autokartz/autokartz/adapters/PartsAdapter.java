package com.autokartz.autokartz.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.autokartz.autokartz.R;
import com.autokartz.autokartz.dialoges.ProductDetailDialog;
import com.autokartz.autokartz.interfaces.GetCarPartsListener;
import com.autokartz.autokartz.services.databases.preferences.AccountDetailHolder;
import com.autokartz.autokartz.utils.pojoClasses.CatItem;
import com.autokartz.autokartz.utils.pojoClasses.CategoryInformation;
import com.autokartz.autokartz.utils.util.constants.AppConstantKeys;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Cortana on 1/13/2018.
 */

public class PartsAdapter extends RecyclerView.Adapter<PartsAdapter.PartsViewHolder> {


    private Context mContext;
    private Activity mActivity;
    private ArrayList<CatItem> mPartsList;
    private CategoryInformation mCategoryInformation;
    private GetCarPartsListener mGetCarPartsListener;
    private ArrayList<CategoryInformation> mSelectedCarPartList;
    private AccountDetailHolder mAccountDetailHolder;

    public PartsAdapter(Context context,Activity activity,GetCarPartsListener getCarPartsListener) {
        mContext=context;
        mActivity=activity;
        mPartsList=new ArrayList<>();
        mAccountDetailHolder=new AccountDetailHolder(mContext);
        mGetCarPartsListener=getCarPartsListener;
    }

    @Override
    public PartsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.part_item_layout,parent,false);
        return new PartsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PartsViewHolder holder, int position) {
        holder.catNameTv.setText(mPartsList.get(position).getName());
        holder.setIsRecyclable(false);
        for(CategoryInformation cat:mSelectedCarPartList) {
            if(cat.getmPartName().equalsIgnoreCase(mPartsList.get(position).getName())) {
                holder.isSelected=true;
                break;
            }
        }
        if(holder.isSelected) {
            holder.dropDownIv.setImageResource(R.drawable.ic_check);
        } else {
            holder.dropDownIv.setImageResource(R.drawable.ic_uncheck);
        }
    }

    @Override
    public int getItemCount() {
        return mPartsList.size();
    }

    public void setPartsList(ArrayList<CatItem> partList) {
        mPartsList=partList;
        mSelectedCarPartList=mAccountDetailHolder.getSelectedCarParts();
    }

    public void setCatInfo(CategoryInformation categoryInformation) {
        mCategoryInformation=categoryInformation;
    }

    public class PartsViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.part_name_tv)
        TextView catNameTv;
        @BindView(R.id.part_drop_down_iv)
        ImageView dropDownIv;
        @BindView(R.id.part_detail_iv)
        ImageView detailIv;
        @BindView(R.id.part_item_rel_layout)
        RelativeLayout itemLayout;
        boolean isSelected=false;

        public PartsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            detailIv.setVisibility(View.VISIBLE);
            itemLayout.setBackgroundResource(R.color.LightSkyBlue_b);
        }

        @OnClick({R.id.part_drop_down_iv,R.id.part_name_tv})
        public void onClickPart() {
           /* new Thread(new Runnable() {
                public void run() {*/
                    mCategoryInformation.setmPartName(mPartsList.get(getAdapterPosition()).getName());
                    mCategoryInformation.setmPartId(mPartsList.get(getAdapterPosition()).getId());
                    mCategoryInformation.setDefined(1);
                    if(isSelected) {
                        dropDownIv.setImageResource(R.drawable.ic_uncheck);
                        isSelected=false;
                        mGetCarPartsListener.getUnSelectedCarPart(mCategoryInformation);
                    } else {
                        dropDownIv.setImageResource(R.drawable.ic_check);
                        isSelected=true;
                        mGetCarPartsListener.getSelectedCarPart(mCategoryInformation);
                    }
                /*}
            }).start();*/

        }

        @OnClick({R.id.part_detail_iv})
        public void onClickDeatilIv(){
            if(isSelected) {
                int position=getAdapterPosition();
                CategoryInformation categoryInformation;
                ArrayList<CategoryInformation> list=mAccountDetailHolder.getSelectedCarParts();
                for(int i=0;i<list.size();i++){
                    categoryInformation=list.get(i);
                    if(categoryInformation.equals(mCategoryInformation)) {
                        ProductDetailDialog dialog=new ProductDetailDialog(mContext,mActivity, AppConstantKeys.SELECTED_PART,i);
                        dialog.show();
                        break;
                    }
                }
            } else {
                Toast.makeText(mContext,"First Select Item",Toast.LENGTH_SHORT).show();
            }
        }

    }

}