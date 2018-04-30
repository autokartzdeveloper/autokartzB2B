package com.autokartz.autokartz.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
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
 * Created by Cortana on 1/4/2018.
 */

public class CategoryCarPartsAdapter extends RecyclerView.Adapter<CategoryCarPartsAdapter.CategoryCarPartsHolder> {


    private Context mContext;
    private Activity mActivity;
    private ArrayList<CatItem> mCatList;
    private DatabaseCURDOperations databaseCURDOperations;
    private CategoryInformation categoryInformation;
    private GetCarPartsListener mGetCarPartsListener;
    private TypedArray icons;

    public CategoryCarPartsAdapter(Context context,Activity activity,GetCarPartsListener getCarPartsListener, ArrayList<CatItem> catList) {
        mContext = context;
        mCatList = catList;
        mActivity=activity;
        mGetCarPartsListener=getCarPartsListener;
        databaseCURDOperations=new DatabaseCURDOperations(mContext);
        categoryInformation=new CategoryInformation();
        icons=mContext.getResources().obtainTypedArray(R.array.catImages);
    }

    @Override
    public CategoryCarPartsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_part_category_layout, parent, false);
        return new CategoryCarPartsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CategoryCarPartsHolder holder, int position) {
        holder.catNameTv.setText(mCatList.get(position).getName());
        holder.dropDownIv.setImageResource(R.drawable.ic_drop_down);
        holder.catImageView.setImageDrawable(icons.getDrawable(position));
        holder.setIsRecyclable(false);
        holder.id=mCatList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return mCatList.size();
    }

    public class CategoryCarPartsHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cat_item_name_tv)
        TextView catNameTv;
        @BindView(R.id.car_cat_drop_down_iv)
        ImageView dropDownIv;
        @BindView(R.id.cat_item_image_view)
        ImageView catImageView;
        @BindView(R.id.sub_cat_recycler_view)
        RecyclerView subCatRv;
        @BindView(R.id.cat_item_rel_layout)
        RelativeLayout itemlayout;
        SubCategoryPartAdapter subCategoryPartAdapter;
        ArrayList<CatItem> subCatList;
        String id;
        String catName;
        boolean isOpen=false;

        public CategoryCarPartsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            //catImageView.setColorFilter(mContext.getResources().getColor(R.color.Blue));
            dropDownIv.setColorFilter(mContext.getResources().getColor(R.color.Gray));
            subCategoryPartAdapter=new SubCategoryPartAdapter(mContext,mActivity,mGetCarPartsListener);
            RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(mContext);
            subCatRv.setLayoutManager(layoutManager);
            subCatRv.setItemAnimator(new DefaultItemAnimator());
            subCatRv.setAdapter(subCategoryPartAdapter);
        }

        @OnClick({R.id.cat_item_name_tv,R.id.car_cat_drop_down_iv,R.id.cat_item_rel_layout})
        public void onCatClick() {
            if(isOpen) {
                dropDownIv.setImageResource(R.drawable.ic_drop_down);
                subCatRv.setVisibility(View.GONE);
                isOpen=false;
            } else {
                int position=getAdapterPosition();
                subCatRv.setVisibility(View.VISIBLE);
                dropDownIv.setImageResource(R.drawable.ic_drop_up);
                isOpen=true;
                catName=catNameTv.getText().toString();
                categoryInformation.setmCatName(catName);
                categoryInformation.setmCatId(id);
                categoryInformation.setImageResId(icons.getResourceId(position,0));
                subCategoryPartAdapter.setCatInfo(categoryInformation);
                subCatList=databaseCURDOperations.getSubCategories(id);
                subCategoryPartAdapter.setSubCatList(subCatList);
                subCategoryPartAdapter.notifyDataSetChanged();
            }
        }

    }

}