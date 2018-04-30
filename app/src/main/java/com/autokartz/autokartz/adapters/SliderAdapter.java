package com.autokartz.autokartz.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autokartz.autokartz.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by autokartz on 28/4/18.
 */

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private List<Integer> imageSlider;


    public SliderAdapter(Context context, List<Integer> imageSlider) {
        this.context = context;
        this.imageSlider = imageSlider;

    }

    @Override
    public int getCount() {
        return imageSlider.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_slider, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.slideimage);
        imageView.setImageResource(imageSlider.get(position));
        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}