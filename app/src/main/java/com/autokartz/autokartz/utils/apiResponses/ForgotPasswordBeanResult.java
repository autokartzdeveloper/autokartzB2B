package com.autokartz.autokartz.utils.apiResponses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2/2/2018.
 */

public class ForgotPasswordBeanResult {


    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("results")
    private ForgotPasswordBeanResponse forgotPasswordBeanResponse;

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

    public ForgotPasswordBeanResponse getForgotPasswordBeanResponse() {
        return forgotPasswordBeanResponse;
    }

    public void setForgotPasswordBeanResponse(ForgotPasswordBeanResponse forgotPasswordBeanResponse) {
        this.forgotPasswordBeanResponse = forgotPasswordBeanResponse;
    }

}