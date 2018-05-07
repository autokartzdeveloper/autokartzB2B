package com.autokartz.autokartz.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.services.databases.preferences.AccountDetailHolder;
import com.autokartz.autokartz.utils.pojoClasses.CarInformation;
import com.autokartz.autokartz.utils.pojoClasses.CategoryInformation;
import com.autokartz.autokartz.utils.util.AppToast;
import com.autokartz.autokartz.utils.util.constants.AppConstantKeys;
import com.autokartz.autokartz.utils.util.constants.IntentKeyConstants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Cortana on 1/3/2018.
 */

public class CarChassisNumberFragment extends Fragment {

    @BindView(R.id.car_chassis_num_et)
    EditText mCarChassisNumEt;
    @BindView(R.id.submit_car_chassis_num_btn)
    Button mSubmitBtn;
    private static final String CURRENT_TAG = "CarChassisNumberFragment";
    private CarInformation mCarInfo;
    private Activity mActivity;
    private AccountDetailHolder mAccountDetailHolder;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_chassis_number_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        mActivity.setTitle("Chassis Number");
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("Chassis Number");
    }

    private void init() {
        initVariables();
    }

    private void initVariables() {
        mContext = getContext();
        mAccountDetailHolder = new AccountDetailHolder(mContext);
        mAccountDetailHolder.setSelectedCarParts(new ArrayList<CategoryInformation>());
        mAccountDetailHolder.setAddPart(new ArrayList<CategoryInformation>());
        mActivity = getActivity();
        mCarInfo = (CarInformation) getArguments().get(IntentKeyConstants.TAG_FORM_DATA);
    }

    @OnClick({R.id.submit_car_chassis_num_btn})
    public void onSubmitButton() {
        String mCarChassisNumber = mCarChassisNumEt.getText().toString();
        if (mCarChassisNumber == null || mCarChassisNumber.isEmpty()) {
            AppToast.showToast(mContext, "Please enter car chassis number");
        } else {
            mCarInfo.setmCarChassisNumber(mCarChassisNumber);
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentKeyConstants.TAG_FORM_DATA, mCarInfo);
            bundle.putString(IntentKeyConstants.KEY_FRAGMENT, AppConstantKeys.TAG_CAR_CHASSIS_NUMBER);
            Fragment fragment = new EnquireCarPartsDetailsFragment();
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_frame, fragment, CURRENT_TAG);
            fragmentTransaction.addToBackStack(CURRENT_TAG);
            fragmentTransaction.commit();
        }
    }

}