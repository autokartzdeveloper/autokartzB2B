package com.autokartz.autokartz.utils.apiResponses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2/2/2018.
 */

public class UserDetailBeanResult {


    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("results")
    private UserDetailBean userDetailBean;

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

    public UserDetailBean getUserDetailBean() {
        return userDetailBean;
    }

    public void setUserDetailBean(UserDetailBean userDetailBean) {
        this.userDetailBean = userDetailBean;
    }

}