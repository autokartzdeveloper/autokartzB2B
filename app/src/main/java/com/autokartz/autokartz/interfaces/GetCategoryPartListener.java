package com.autokartz.autokartz.interfaces;

import com.autokartz.autokartz.utils.apiResponses.CategoryPartResultBean;

import java.util.ArrayList;

/**
 * Created by user on 2/21/2018.
 */

public interface GetCategoryPartListener {

    void getCatPartList(boolean isReceived, ArrayList<CategoryPartResultBean> list);
}