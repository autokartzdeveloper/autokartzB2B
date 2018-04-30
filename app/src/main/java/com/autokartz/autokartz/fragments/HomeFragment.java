package com.autokartz.autokartz.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.activities.MainDashboardActivity;
import com.autokartz.autokartz.adapters.SliderAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Cortana on 1/3/2018.
 */

public class HomeFragment extends Fragment {


    private Activity mActivity;
    @BindView(R.id.home_enquiry_btn)
    Button mEnquiryButoon;
    @BindView(R.id.home_order_btn)
    Button mOrderButoon;
    private static final String CURRENT_TAG = "Home Fragment";
    ViewPager viewPager;
    TabLayout indicator;
    List<Integer> imageSlider;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.indicator)
    TabLayout mIndicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("Home");

    }

    private void init() {
        initVariables();
    }

    private void imageSlideTimer() {

        imageSlider = new ArrayList<>();
        imageSlider.add(R.drawable.banner);
        imageSlider.add(R.drawable.banner2);
        imageSlider.add(R.drawable.banner_3);
        imageSlider.add(R.drawable.banner4);
        mViewPager.setAdapter(new SliderAdapter(mActivity, imageSlider));
        mIndicator.setupWithViewPager(mViewPager, true);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 2000, 3000);
    }


    private void initVariables() {
        mActivity = getActivity();
        imageSlideTimer();
    }

    @OnClick({R.id.home_order_btn})
    public void onClickOrders() {
        openOrdersFragment();
    }

    private void openOrdersFragment() {

        OrdersFragment fragment = new OrdersFragment();
        String backStateName = fragment.getClass().getName();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_frame, fragment, CURRENT_TAG);
        fragmentTransaction.addToBackStack(CURRENT_TAG);
        fragmentTransaction.commit();
    }

    @OnClick({R.id.home_enquiry_btn})
    public void onClickAddEnquiry() {
        openEnquiryFormFragment();
    }

    private void openEnquiryFormFragment() {
        EnquiryFormFragment fragment = new EnquiryFormFragment();
        String backStateName = fragment.getClass().getName();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment, backStateName);
        fragmentTransaction.addToBackStack(backStateName);
        fragmentTransaction.commit();
    }

    private class SliderTimer extends TimerTask {
        @Override
        public void run() {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mViewPager.getCurrentItem() < imageSlider.size() - 1) {
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                    } else {
                        mViewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

}