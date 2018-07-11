package com.autokartz.autokartz.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.adapters.SliderAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Cortana on 1/3/2018.
 */

public class HomeFragment extends Fragment {
    private Activity mActivity;
    private static final String CURRENT_TAG = "Home Fragment";
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
        offerLayout();
    }

    private void offerLayout() {
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