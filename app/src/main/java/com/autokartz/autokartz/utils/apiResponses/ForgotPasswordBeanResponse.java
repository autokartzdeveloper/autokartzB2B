package com.autokartz.autokartz.utils.apiResponses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2/2/2018.
 */

public class ForgotPasswordBeanResponse {


    @SerializedName("user_id")
    private String userId ;

    @SerializedName("otp")
    private String otp;


    public String getOtp() {
        return otp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }


}