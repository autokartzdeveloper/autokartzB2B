package com.autokartz.autokartz.utils.apiResponses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Cortana on 3/15/2018.
 */

public class ProductOrderAPIResponse {


    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}