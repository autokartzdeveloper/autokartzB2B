package com.autokartz.autokartz.utils.apiRequestBeans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2/3/2018.
 */

public class ForgotPaswordBean {

    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;


    public ForgotPaswordBean(String email, String phone) {
        this.email = email;

        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}