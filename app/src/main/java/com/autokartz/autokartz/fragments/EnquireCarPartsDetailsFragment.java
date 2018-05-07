package com.autokartz.autokartz.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.adapters.EnquiryCarPartsDetailsAdapter;
import com.autokartz.autokartz.adapters.EnquiryFormAddedPartAdapter;
import com.autokartz.autokartz.interfaces.GetCategoryPartListener;
import com.autokartz.autokartz.interfaces.GetEnquiryApiResponseListener;
import com.autokartz.autokartz.services.databases.LocalDatabase.DatabaseCURDOperations;
import com.autokartz.autokartz.services.databases.preferences.AccountDetailHolder;
import com.autokartz.autokartz.services.webServices.apiRequests.EnquiryApi;
import com.autokartz.autokartz.services.webServices.apiRequests.GetCategoryPartApi;
import com.autokartz.autokartz.utils.apiResponses.CategoryPartResultBean;
import com.autokartz.autokartz.utils.pojoClasses.CarInformation;
import com.autokartz.autokartz.utils.pojoClasses.CategoryInformation;
import com.autokartz.autokartz.utils.util.constants.AppConstantKeys;
import com.autokartz.autokartz.utils.util.constants.IntentKeyConstants;
import com.autokartz.autokartz.utils.util.dialogs.DismissDialog;
import com.autokartz.autokartz.utils.util.dialogs.ShowDialog;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Cortana on 1/5/2018.
 */

public class EnquireCarPartsDetailsFragment extends Fragment implements GetCategoryPartListener, GetEnquiryApiResponseListener {

    @BindView(R.id.add_car_parts_btn)
    Button mAddCarPartsBtn;
    @BindView(R.id.submit_enquire_parts_btn)
    Button mSubmitCarPartsBtn;
    @BindView(R.id.car_chassis_number_value_tv)
    TextView mCarChassisNumValTv;
    @BindView(R.id.car_brand_val_tv)
    TextView mCarBrandValTv;
    @BindView(R.id.car_model_val_tv)
    TextView mCarModelValTv;
    @BindView(R.id.car_varient_val_tv)
    TextView mCarVarientTv;
    @BindView(R.id.car_engine_val_tv)
    TextView mCarEngineTv;
    @BindView(R.id.car_year_val_tv)
    TextView mCarYearValTv;
    @BindView(R.id.selected_car_parts_rv)
    RecyclerView mSelectedCarPartsRv;
    @BindView(R.id.enquiry_form_added_part_rv)
    RecyclerView mAddedPartRv;
    private AccountDetailHolder mAccountDetailHolder;
    private CarInformation mCarInfo;
    private ProgressDialog mProgressDialog;
    private EnquiryCarPartsDetailsAdapter enquiryCarPartsDetailsAdapter;
    private static final String CURRENT_TAG = "EnquireCarPartsDetailsFragment";
    private ArrayList<String> partsList;
    private Context mContext;
    private Activity mActivity;
    private ArrayList<CategoryInformation> mRequirePartsList = new ArrayList<>();
    private EnquiryFormAddedPartAdapter mEnquiryFormAddedPartAdapter;
    private ArrayList<CategoryInformation> mAddedPartList;
    private DatabaseCURDOperations mDatabaseCURDOperations;
    ArrayList<CategoryInformation> fullList;
    ArrayList<CategoryInformation> mSelectedParts;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enquiry_form_details, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("Parts Requirement Details");
    }

    private void init() {
        initVariables();
        setViews();
        setSelectedPartRecyclerView();
        setAddedPartRecyclerView();
    }

    private void setAddedPartRecyclerView() {
        mEnquiryFormAddedPartAdapter = new EnquiryFormAddedPartAdapter(mContext, mActivity, mSubmitCarPartsBtn, mAddCarPartsBtn,enquiryCarPartsDetailsAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mAddedPartRv.setLayoutManager(layoutManager);
        mAddedPartRv.setItemAnimator(new DefaultItemAnimator());
        mAddedPartRv.setAdapter(mEnquiryFormAddedPartAdapter);
    }

    private void setViews() {
        mCarChassisNumValTv.setText(mCarInfo.getmCarChassisNumber());
        mCarBrandValTv.setText(mCarInfo.getmBarnd());
        mCarModelValTv.setText(mCarInfo.getmModel());
        mCarVarientTv.setText(mCarInfo.getmVariant());
        mCarEngineTv.setText(mCarInfo.getmEnginne());
        mCarYearValTv.setText(mCarInfo.getmYear());
        mRequirePartsList = mCarInfo.getmRequirePartsList();
        if (mRequirePartsList == null) {
            mRequirePartsList = new ArrayList<>();

        }
        mRequirePartsList.size();


    }

    private void setSelectedPartRecyclerView() {
        enquiryCarPartsDetailsAdapter = new EnquiryCarPartsDetailsAdapter(mActivity, mSubmitCarPartsBtn, mAddCarPartsBtn);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mSelectedCarPartsRv.setLayoutManager(layoutManager);
        mSelectedCarPartsRv.setItemAnimator(new DefaultItemAnimator());
        mSelectedCarPartsRv.setAdapter(enquiryCarPartsDetailsAdapter);
    }

    private void initVariables() {
        mContext = getContext();
        mActivity = getActivity();
        mAccountDetailHolder = new AccountDetailHolder(mContext);
        mDatabaseCURDOperations = new DatabaseCURDOperations(mContext);
        mSubmitCarPartsBtn.setVisibility(View.GONE);
        mAddCarPartsBtn.setBackgroundColor(getResources().getColor(R.color.appcolorornage));
        mAddCarPartsBtn.setTextColor(getResources().getColor(R.color.White));
        mCarInfo = (CarInformation) getArguments().getSerializable(IntentKeyConstants.TAG_FORM_DATA);
        String tag = getArguments().getString(IntentKeyConstants.KEY_FRAGMENT);
        if (tag != null && tag.equalsIgnoreCase(AppConstantKeys.TAG_PART_SUGGESTION)) {
            mAccountDetailHolder.setSelectedCarParts(mCarInfo.getmRequirePartsList());
        }
        partsList = new ArrayList<>();
        mAddedPartList = mAccountDetailHolder.getAddPartDetails();
        mSelectedParts = mAccountDetailHolder.getSelectedCarParts();

    }

    @OnClick({R.id.add_car_parts_btn})
    public void onClickAddPartsButton() {
        if (true) {
            mProgressDialog = ShowDialog.show(mContext, "", "Please Wait", true, false);
            GetCategoryPartApi getCategoryPartApi = new GetCategoryPartApi(mContext, this, mProgressDialog);
            getCategoryPartApi.callCategoryApi();
        } else {

            openCategoryPartFragment();
        }
        //todo update part list in database
        //mCarInfo.setmRequirePartsList(partsList);

    }

    @OnClick({R.id.submit_enquire_parts_btn})
    public void OnClickSubmitBtn() {
        mCarInfo.setmUserId(mAccountDetailHolder.getUserDetailBean().getUserId());
        fullList = mAccountDetailHolder.getSelectedCarParts();
        fullList.addAll(mAccountDetailHolder.getAddPartDetails());
        mCarInfo.setmRequirePartsList(fullList);
        mProgressDialog = ShowDialog.show(mContext, "", "Please Wait", true, false);
        EnquiryApi enquiryApi = new EnquiryApi(mContext, this, mProgressDialog);
        enquiryApi.callEnquiryApi(mCarInfo);
        mAccountDetailHolder.setSelectedCarParts(new ArrayList<CategoryInformation>());
        mAccountDetailHolder.setAddPart(new ArrayList<CategoryInformation>());
    }

    public void openCategoryPartFragment() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentKeyConstants.TAG_FORM_DATA, mCarInfo);
        Fragment fragment = new CategoryCarPartsFragment();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment, CURRENT_TAG);
        fragmentTransaction.addToBackStack(CURRENT_TAG);
        fragmentTransaction.commit();
    }

    @Override
    public void getCatPartList(boolean isReceived, ArrayList<CategoryPartResultBean> list) {
        if (isReceived) {
            CategoryInformation categoryInformation;
            mDatabaseCURDOperations.deleteCatPartInfo();
            for (CategoryPartResultBean catPart : list) {
                categoryInformation = new CategoryInformation(catPart.getCatId(), catPart.getCatName(), catPart.getSubCatId(), catPart.getSubCatName(), catPart.getPartId(), catPart.getPartName(), 1);
                mDatabaseCURDOperations.insertCatInfo(categoryInformation);

            }
        }
        openCategoryPartFragment();
    }

    @Override
    public void getEnquiryApiResponse(boolean isSent) {
        if (isSent) {
            mAccountDetailHolder.setSelectedCarParts(new ArrayList<CategoryInformation>());
            FragmentManager fm1 = getActivity().getSupportFragmentManager();
            fm1.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

}