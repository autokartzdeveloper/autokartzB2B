package com.autokartz.autokartz.utils.apiResponses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 3/22/2018.
 */

public class ProductSuggestionResponseBean {


    @SerializedName("success")
    private boolean isSuccess;
    @SerializedName("result")
    private EnquirySuggestionResponseBean enquirySuggestionResponseBean;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public EnquirySuggestionResponseBean getEnquirySuggestionResponseBean() {
        return enquirySuggestionResponseBean;
    }

    public void setEnquirySuggestionResponseBean(EnquirySuggestionResponseBean enquirySuggestionResponseBean) {
        this.enquirySuggestionResponseBean = enquirySuggestionResponseBean;
    }

}