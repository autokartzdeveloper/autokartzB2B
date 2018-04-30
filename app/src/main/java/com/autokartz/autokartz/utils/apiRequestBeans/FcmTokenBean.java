package com.autokartz.autokartz.utils.apiRequestBeans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2/3/2018.
 */

public class FcmTokenBean {


    @SerializedName("user_id")
    private String user_id;

    @SerializedName("token")
    private String token;

    public FcmTokenBean(String user_id, String token) {
        this.user_id = user_id;
        this.token = token;

    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}