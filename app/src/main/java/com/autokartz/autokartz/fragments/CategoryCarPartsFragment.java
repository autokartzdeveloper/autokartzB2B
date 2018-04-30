package com.autokartz.autokartz.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.adapters.CategoryCarAddPartAdapter;
import com.autokartz.autokartz.adapters.CategoryCarPartsAdapter;
import com.autokartz.autokartz.interfaces.GetCarPartsListener;
import com.autokartz.autokartz.services.databases.LocalDatabase.DatabaseCURDOperations;
import com.autokartz.autokartz.services.databases.preferences.AccountDetailHolder;
import com.autokartz.autokartz.utils.pojoClasses.CatItem;
import com.autokartz.autokartz.utils.pojoClasses.CategoryInformation;
import com.autokartz.autokartz.utils.util.dialogs.DismissDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Cortana on 1/4/2018.
 */

public class CategoryCarPartsFragment extends Fragment implements GetCarPartsListener {

    @BindView(R.id.cat_list_recycler_view)
    RecyclerView mCatListRv;
    @BindView(R.id.cat_list_next_btn)
    Button mNextBtn;
    @BindView(R.id.category_add_parts_rv)
    RecyclerView mAddPartRv;
    @BindView(R.id.category_car_add_part_iv)
    ImageView mAddPartiv;
    private List catInfoList;
    private ArrayList<CatItem> catList;
    private Context mContext;
    private Activity mActivity;
    private ProgressDialog progressDialog;
    private static final String LOG_TAG = CategoryCarPartsFragment.class.getName();
    private DatabaseCURDOperations databaseCURDOperations;
    private CategoryCarPartsAdapter mCategoryCarPartsAdapter;
    private CategoryCarAddPartAdapter mCategoryCarAddPartAdapter;
    private ArrayList<CategoryInformation> selectedCarPartList, addedPartList;
    private AccountDetailHolder mAccountDetailHolder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_parts, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("Car Parts");
        init();
    }

    private void init() {
        initVariables();
        setCategoryPartRecyclerView();
        setCategoryAddPartRecyclerView();
    }

    private void setCategoryAddPartRecyclerView() {
        mCategoryCarAddPartAdapter = new CategoryCarAddPartAdapter(mContext, mActivity, addedPartList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mAddPartRv.setLayoutManager(mLayoutManager);
        mAddPartRv.setItemAnimator(new DefaultItemAnimator());
        mAddPartRv.setAdapter(mCategoryCarAddPartAdapter);
        /*mCategoryCarAddPartAdapter.updateAddedPartList();
        mCategoryCarAddPartAdapter.notifyDataSetChanged();*/
    }

    private void setCategoryPartRecyclerView() {
        mCategoryCarPartsAdapter = new CategoryCarPartsAdapter(mContext, mActivity, this, catList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mCatListRv.setLayoutManager(mLayoutManager);
        mCatListRv.setItemAnimator(new DefaultItemAnimator());
        mCatListRv.setAdapter(mCategoryCarPartsAdapter);
    }

    private void initVariables() {
        mContext = getContext();
        mActivity = getActivity();
        catInfoList = new ArrayList<>();
        mAccountDetailHolder = new AccountDetailHolder(mContext);
        catList = new ArrayList<>();

        databaseCURDOperations = new DatabaseCURDOperations(mContext);
        if (!mAccountDetailHolder.getIsCatInfoLoaded()) {
            //readCatInfoExcelFile();
            /*ReadCatInfoExcelFile readCatInfoExcelFile=new ReadCatInfoExcelFile();
            readCatInfoExcelFile.execute();*/
        }
        selectedCarPartList = new ArrayList<>();
        addedPartList = mAccountDetailHolder.getAddPartDetails();
        catList = databaseCURDOperations.getCarCategories();
    }

    @OnClick({R.id.cat_list_next_btn})
    public void onClickNextButton() {
        mAccountDetailHolder.setAddPart(mCategoryCarAddPartAdapter.updateAddedPartList());
        getFragmentManager().popBackStack();
    }

    @OnClick({R.id.category_car_add_part_iv})
    public void onClickAddPartIv() {
        mCategoryCarAddPartAdapter.addAddPart();
    }

    @Override
    public void getSelectedCarPart(CategoryInformation categoryInformation) {
        ArrayList<CategoryInformation> list = mAccountDetailHolder.getSelectedCarParts();
        list.add(categoryInformation);
        mAccountDetailHolder.setSelectedCarParts(list);
        list.clear();
    }

    @Override
    public void getUnSelectedCarPart(CategoryInformation categoryInformation) {
        ArrayList<CategoryInformation> list = mAccountDetailHolder.getSelectedCarParts();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(categoryInformation)) {
                list.contains(categoryInformation);
                list.remove(i);
                break;
            }
        }
        mAccountDetailHolder.setSelectedCarParts(list);
        list.clear();
    }

    @Override
    public void onPause() {
        mAccountDetailHolder.setAddPart(mCategoryCarAddPartAdapter.updateAddedPartList());
        super.onPause();
    }

    private class ReadCatInfoExcelFile extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //readCatInfoExcelFile();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            DismissDialog.dismissWithCheck(progressDialog);
        }

    }

}