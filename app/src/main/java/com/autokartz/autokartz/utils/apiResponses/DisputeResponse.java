package com.autokartz.autokartz.utils.apiResponses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by autokartz on 5/4/18.
 */

public class DisputeResponse {


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
