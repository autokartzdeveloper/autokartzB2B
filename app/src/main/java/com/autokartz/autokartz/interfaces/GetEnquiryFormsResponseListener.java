package com.autokartz.autokartz.interfaces;

import com.autokartz.autokartz.utils.apiResponses.EnquiryFormsResponseBean;

import java.util.ArrayList;

/**
 * Created by user on 2/26/2018.
 */

public interface GetEnquiryFormsResponseListener {

    void getEnquiryFormReponse(boolean isSuccess, ArrayList<EnquiryFormsResponseBean> list);
}