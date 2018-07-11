package com.autokartz.autokartz.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.adapters.PartListAdapter;
import com.autokartz.autokartz.interfaces.SuggestionResponseListener;
import com.autokartz.autokartz.services.databases.preferences.AccountDetailHolder;
import com.autokartz.autokartz.services.webServices.apiRequests.SuggestionApi;
import com.autokartz.autokartz.utils.apiResponses.EnquiryPartSuggestionResponseBean;
import com.autokartz.autokartz.utils.apiResponses.EnquirySuggestionResponseBean;
import com.autokartz.autokartz.utils.apiResponses.SuggestionResponseBean;
import com.autokartz.autokartz.utils.pojoClasses.CarInformation;
import com.autokartz.autokartz.utils.pojoClasses.CategoryInformation;
import com.autokartz.autokartz.utils.util.AppToast;
import com.autokartz.autokartz.utils.util.constants.AppConstantKeys;
import com.autokartz.autokartz.utils.util.constants.IntentKeyConstants;
import com.autokartz.autokartz.utils.util.dialogs.DismissDialog;
import com.autokartz.autokartz.utils.util.dialogs.ShowDialog;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 3/1/2018.
 */

public class PartSuggestionFragment extends Fragment implements SuggestionResponseListener {

    public static final String CURRENT_TAG = PartSuggestionFragment.class.getName();
    @BindView(R.id.parts_list_rv)
    RecyclerView mPartListRv;
    @BindView(R.id.update_form_btn)
    Button mUpdateFormBtn;
    @BindView(R.id.submit_order_btn)
    Button mSubmitOrderBtn;
    @BindView(R.id.part_suggestion_no_item_text_tv)
    TextView mNoItemTextTv;
    @BindView(R.id.car_chassis_num_tv)
    TextView mCarChassisNumTv;
    @BindView(R.id.car_brand_tv)
    TextView mCarBrandTv;
    @BindView(R.id.car_model_tv)
    TextView mCarModelTv;
    @BindView(R.id.car_varient_tv)
    TextView mCarVarientTv;
    @BindView(R.id.car_engine_tv)
    TextView mCarEngineTv;
    @BindView(R.id.car_year_tv)
    TextView mCarYearTv;
    @BindView(R.id.car_chassis_number_value_tv)
    TextView mCarChassisNumValTv;
    @BindView(R.id.car_brand_val_tv)
    TextView mCarBrandValTv;
    @BindView(R.id.car_model_val_tv)
    TextView mCarModelValTv;
    @BindView(R.id.car_varient_val_tv)
    TextView mCarVarientValTv;
    @BindView(R.id.car_engine_val_tv)
    TextView mCarEngineValTv;
    @BindView(R.id.car_year_val_tv)
    TextView mCarYearValTv;
    String carChassis;
    private Context mContext;
    private Activity mActivity;
    private AccountDetailHolder mAccountDetailHolder;
    private String enquiryId;
    private PartListAdapter mPartListAdapter;
    private ProgressDialog mProgressDialog;
    private CarInformation carInformation;
    private ArrayList<SuggestionResponseBean> mOrderList;
    private boolean isResponseReceived;
    private ArrayList<EnquiryPartSuggestionResponseBean> mPartSuggestionList;
    String codstatus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part_suggestion, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("Part Suggestions");
        init();
    }

    private void init() {
        setVisibility();
        initVariables();
        callSuggestionApi();
        setRecyclerView();
    }

    private void setVisibility() {
        mNoItemTextTv.setVisibility(View.VISIBLE);
        mPartListRv.setVisibility(View.GONE);
        mUpdateFormBtn.setVisibility(View.GONE);
        mSubmitOrderBtn.setVisibility(View.GONE);
        mCarChassisNumValTv.setVisibility(View.GONE);
        mCarBrandValTv.setVisibility(View.GONE);
        mCarModelValTv.setVisibility(View.GONE);
        mCarVarientValTv.setVisibility(View.GONE);
        mCarEngineValTv.setVisibility(View.GONE);
        mCarYearValTv.setVisibility(View.GONE);
        mCarChassisNumTv.setVisibility(View.GONE);
        mCarBrandTv.setVisibility(View.GONE);
        mCarModelTv.setVisibility(View.GONE);
        mCarVarientTv.setVisibility(View.GONE);
        mCarEngineTv.setVisibility(View.GONE);
        mCarYearTv.setVisibility(View.GONE);
    }

    private void setRecyclerView() {
        mPartListAdapter = new PartListAdapter(mContext, mSubmitOrderBtn, mUpdateFormBtn);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mPartListRv.setLayoutManager(layoutManager);
        mPartListRv.setItemAnimator(new DefaultItemAnimator());
        mPartListRv.setAdapter(mPartListAdapter);
    }

    public void callSuggestionApi() {
        mProgressDialog = ShowDialog.show(mContext, "", "Please Wait", true, false);
        String userId = mAccountDetailHolder.getUserDetailBean().getUserId();
        SuggestionApi suggestionApi = new SuggestionApi(mContext, mProgressDialog, this);
        suggestionApi.callSuggestionApi(userId, enquiryId);
    }

    private void initVariables() {
        mContext = getContext();
        mActivity = getActivity();
        mPartSuggestionList = new ArrayList<>();
        mOrderList = new ArrayList<>();
        carInformation = new CarInformation();
        mAccountDetailHolder = new AccountDetailHolder(mContext);
        enquiryId = getArguments().getString(IntentKeyConstants.TAG_ENQUIRY_ID);
        carInformation.setmEnquiry(enquiryId);
        // enquiryId = getArguments().getString("enq_id");
    }

    @OnClick({R.id.submit_order_btn})
    public void onClickSubmitOrder() {
        //fragment.setArguments(bundle1);
        mOrderList.clear();
        mOrderList.addAll(mPartListAdapter.getOrderList());
        if (mOrderList.size() > 0) {
            Fragment fragment = new InvoiceOrderFragment();
            Bundle bundle = new Bundle();
            Gson gson = new Gson();
            String jsonDetails = gson.toJson(mOrderList);
            bundle.putString(IntentKeyConstants.TAG_ORDER_LIST, jsonDetails);
            bundle.putString(IntentKeyConstants.TAG_ORDER_COD, codstatus);
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_frame, fragment, CURRENT_TAG);
            fragmentTransaction.addToBackStack(CURRENT_TAG);
            fragmentTransaction.commit();
        } else {
            AppToast.showToast(mContext, "Couldn't generate empty invoice order. Please select products");
        }
    }

    @OnClick({R.id.update_form_btn})
    public void onClickUpdateForm() {
        if (isResponseReceived) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentKeyConstants.TAG_FORM_DATA, carInformation);
            bundle.putString(IntentKeyConstants.KEY_FRAGMENT, AppConstantKeys.TAG_PART_SUGGESTION);
            Fragment fragment = new EnquireCarPartsDetailsFragment();
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_frame, fragment, CURRENT_TAG);
            fragmentTransaction.addToBackStack(CURRENT_TAG);
            fragmentTransaction.commit();
        } else {
            AppToast.showToast(mContext, "Data Not Found");
        }
    }

    @Override
    public void getSuggestionResponse(EnquirySuggestionResponseBean enquirySuggestionResponseBean, boolean isReceived) {
        isResponseReceived = isReceived;
        if (isReceived) {
            ArrayList<EnquiryPartSuggestionResponseBean> list = enquirySuggestionResponseBean.getmEnquirySuggestionPartResponseBeanList();
            getRequirementDataFromResponse(enquirySuggestionResponseBean);
            if (list.size() > 0) {
                updateVisibility();
                mPartSuggestionList.clear();
                mPartSuggestionList.addAll(list);
                mPartListAdapter.setmEnquiryFormsList(mPartSuggestionList);
                mPartListAdapter.notifyDataSetChanged();

                //mPartSuggestionList.get(0).getSuggestionList()
            } else {
                mNoItemTextTv.setVisibility(View.VISIBLE);
                mPartListRv.setVisibility(View.GONE);

            }
        }
        DismissDialog.dismissWithCheck(mProgressDialog);
    }

    private void updateVisibility() {
        mNoItemTextTv.setVisibility(View.GONE);
        mPartListRv.setVisibility(View.VISIBLE);
        mUpdateFormBtn.setVisibility(View.VISIBLE);
        mSubmitOrderBtn.setVisibility(View.VISIBLE);
        mCarChassisNumValTv.setVisibility(View.VISIBLE);
        mCarBrandValTv.setVisibility(View.VISIBLE);
        mCarModelValTv.setVisibility(View.VISIBLE);
        mCarVarientValTv.setVisibility(View.VISIBLE);
        mCarEngineValTv.setVisibility(View.VISIBLE);
        mCarYearValTv.setVisibility(View.VISIBLE);
        mCarChassisNumTv.setVisibility(View.VISIBLE);
        mCarBrandTv.setVisibility(View.VISIBLE);
        mCarModelTv.setVisibility(View.VISIBLE);
        mCarVarientTv.setVisibility(View.VISIBLE);
        mCarEngineTv.setVisibility(View.VISIBLE);
        mCarYearTv.setVisibility(View.VISIBLE);
    }

    private void getRequirementDataFromResponse(EnquirySuggestionResponseBean enquirySuggestionResponseBean) {
        carChassis = enquirySuggestionResponseBean.getCarChassisNumber();
        mCarChassisNumValTv.setText(carChassis);
        mCarBrandValTv.setText(enquirySuggestionResponseBean.getBrand());
        mCarModelValTv.setText(enquirySuggestionResponseBean.getModel());
        mCarVarientValTv.setText(enquirySuggestionResponseBean.getVariant());
        mCarEngineValTv.setText(enquirySuggestionResponseBean.getEngine());
        mCarYearValTv.setText(enquirySuggestionResponseBean.getYear());
        carInformation.setmCarChassisNumber(enquirySuggestionResponseBean.getCarChassisNumber());
        carInformation.setmBarnd(enquirySuggestionResponseBean.getBrand());
        carInformation.setmModel(enquirySuggestionResponseBean.getModel());
        carInformation.setmVariant(enquirySuggestionResponseBean.getVariant());
        carInformation.setmYear(enquirySuggestionResponseBean.getYear());
        carInformation.setmEnginne(enquirySuggestionResponseBean.getEngine());
        codstatus = enquirySuggestionResponseBean.getCodStatus();
        ArrayList<EnquiryPartSuggestionResponseBean> partSggestionBeanList = enquirySuggestionResponseBean.getmEnquirySuggestionPartResponseBeanList();
        ArrayList<CategoryInformation> requirePartList = new ArrayList<>();
        if (partSggestionBeanList.size() > 0) {
            CategoryInformation categoryInformation;
            for (int i = 0; i < partSggestionBeanList.size(); i++) {
                categoryInformation = new CategoryInformation();
                categoryInformation.setmPartId(partSggestionBeanList.get(i).getPartId());
                categoryInformation.setmPartName(partSggestionBeanList.get(i).getPartName());
                categoryInformation.setmProductDetail(partSggestionBeanList.get(i).getRequirementDetail());
                requirePartList.add(categoryInformation);
                //todo add image path list in category information
            }
            carInformation.setmRequirePartsList(requirePartList);
        }
    }


}