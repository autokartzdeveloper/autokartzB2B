package com.autokartz.autokartz.fragments;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.services.databases.LocalDatabase.DatabaseCURDOperations;
import com.autokartz.autokartz.utils.pojoClasses.CarInformation;
import com.autokartz.autokartz.utils.util.AppToast;
import com.autokartz.autokartz.utils.util.constants.IntentKeyConstants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class EnquiryFormFragment extends Fragment {
    @BindView(R.id.carbrand_spinner)
    Spinner mCarBraND;
    @BindView(R.id.modelcar_spinner)
    Spinner mCarModel;
    @BindView(R.id.variant_spinner)
    Spinner mVarient;
    @BindView(R.id.engine_spinner)
    Spinner mEngine;
    @BindView(R.id.year_spinner)
    Spinner mYear;
    @BindView(R.id.carsubmit_btn)
    Button mSubmitButoon;
    private ArrayAdapter<String> carbrandadapter, caryearadapter, carmodeladapter, carvariantadapter, carengineadapter;
    private Context mContext;
    private Activity mActivity;
    private CarInformation mCarInfo;
    public static final String CURRENT_TAG = "EnquiryFormFragment";
    private static final String LOG_TAG = EnquiryFormFragment.class.getName();
    private ArrayList<String> carBrandList, carModelList, carVariantList, carEngineList, carYearList;
    private DatabaseCURDOperations databaseCURDOperations;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enquiry, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initVariables();
        setSpinnerAdapters();
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("Enquiry Form");
    }

    private void setSpinnerAdapters() {
        carbrandadapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0 && super.isEnabled(position);
            }
        };
        carbrandadapter.clear();
        carbrandadapter.add("Select Car Brand");
        carbrandadapter.addAll(carBrandList);
        mCarBraND.setAdapter(carbrandadapter);

        caryearadapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0 && super.isEnabled(position);
            }
        };
        caryearadapter.add("Select Car Year");
        mYear.setAdapter(caryearadapter);

        carmodeladapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0 && super.isEnabled(position);
            }
        };
        carmodeladapter.add("Select Car Model");
        mCarModel.setAdapter(carmodeladapter);

        carvariantadapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0 && super.isEnabled(position);
            }
        };
        carvariantadapter.add("Select Car Variant");
        mVarient.setAdapter(carvariantadapter);

        carengineadapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0 && super.isEnabled(position);
            }
        };
        carengineadapter.add("Select Car Engine");
        mEngine.setAdapter(carengineadapter);
    }

    private void initVariables() {
        mContext = getContext();
        mActivity = getActivity();
        carBrandList = new ArrayList<>();
        carModelList = new ArrayList<>();
        carVariantList = new ArrayList<>();
        carEngineList = new ArrayList<>();
        carYearList = new ArrayList<>();
        databaseCURDOperations = new DatabaseCURDOperations(mContext);
        carBrandList = databaseCURDOperations.getCarBrands();
        mCarInfo = new CarInformation();
        //  carcategoryadapter = ArrayAdapter.createFromResource(getActivity(), R.array.CAR_CATEGORY, R.layout.support_simple_spinner_dropdown_item);
    }

    @OnItemSelected({R.id.carbrand_spinner})
    public void onSelectedCarBrand(AdapterView<?> parent, View view, int position, long id) {
        mCarInfo.setmBarnd(parent.getItemAtPosition(position).toString());
        //for sqlite
        carModelList = databaseCURDOperations.getCarModels(mCarInfo.getmBarnd());
        carmodeladapter.clear();
        carmodeladapter.add("Select Car Model");
        carvariantadapter.clear();
        carvariantadapter.add("Select Car Variant");
        carengineadapter.clear();
        carengineadapter.add("Select Car Engine");
        caryearadapter.clear();
        caryearadapter.add("Select Car Year");
        carmodeladapter.addAll(carModelList);
        carmodeladapter.notifyDataSetChanged();
        //mCarBraND.setOnItemSelectedListener(this);
    }

    @OnItemSelected({R.id.modelcar_spinner})
    public void onSelectedCarModel(AdapterView<?> parent, View view, int position, long id) {
        mCarInfo.setmModel(parent.getItemAtPosition(position).toString());
        carVariantList = databaseCURDOperations.getCarVariants(mCarInfo.getmBarnd(), mCarInfo.getmModel());
        carvariantadapter.clear();
        carvariantadapter.add("Select Car Variant");

        carengineadapter.clear();
        carengineadapter.add("Select Car Engine");
        caryearadapter.clear();
        caryearadapter.add("Select Car Year");
        carvariantadapter.addAll(carVariantList);
        carvariantadapter.notifyDataSetChanged();
    }

    @OnItemSelected({R.id.variant_spinner})
    public void onSelectedCarVariant(AdapterView<?> parent, View view, int position, long id) {
        mCarInfo.setmVariant(parent.getItemAtPosition(position).toString());
        carEngineList = databaseCURDOperations.getCarEngine(mCarInfo.getmBarnd(), mCarInfo.getmModel(), mCarInfo.getmVariant());
        carengineadapter.clear();
        carengineadapter.add("Select Car Engine");
        caryearadapter.clear();
        caryearadapter.add("Select Car Year");
        carengineadapter.addAll(carEngineList);
        carengineadapter.notifyDataSetChanged();
    }

    @OnItemSelected({R.id.engine_spinner})
    public void onSelectedCarEngine(AdapterView<?> parent, View view, int position, long id) {
        mCarInfo.setmEnginne(parent.getItemAtPosition(position).toString());
        carYearList = databaseCURDOperations.getCarYear(mCarInfo.getmBarnd(), mCarInfo.getmModel(), mCarInfo.getmVariant(), mCarInfo.getmEnginne());
        caryearadapter.clear();
        caryearadapter.add("Select Car Year");
        caryearadapter.addAll(carYearList);
        caryearadapter.notifyDataSetChanged();
    }

    @OnItemSelected({R.id.year_spinner})
    public void onSelectedCarYear(AdapterView<?> parent, View view, int position, long id) {
        mCarInfo.setmYear(parent.getItemAtPosition(position).toString());
    }

    @OnClick({R.id.carsubmit_btn})
    public void onSubmitButton() {
        //validation in all spinner
        if (mCarInfo.getmBarnd().matches("Select Car Brand")) {
            AppToast.showToast(mContext, "Please enter all car details");
            return;
        }
        if (mCarInfo.getmModel().matches("Select Car Model")) {
            AppToast.showToast(mContext, "Please enter all car details");
            return;
        }
        if (mCarInfo.getmVariant().matches("Select Car Variant")) {
            AppToast.showToast(mContext, "Please enter all car details");
            return;
        }
        if (mCarInfo.getmEnginne().matches("Select Car Engine")) {
            AppToast.showToast(mContext, "Please enter all car details");
            return;
        }
        if (mCarInfo.getmYear().matches("Select Car Year")) {
            AppToast.showToast(mContext, "Please enter all car details");
            return;
        } else {
            Log.e("EnquiryFormFragment", "onSubmitButton");
            Fragment fragment = new CarChassisNumberFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentKeyConstants.TAG_FORM_DATA, mCarInfo);
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_frame, fragment, CURRENT_TAG);
            fragmentTransaction.addToBackStack(CURRENT_TAG);
            fragmentTransaction.commit();
            // todo pass car info data

        }
    }
}