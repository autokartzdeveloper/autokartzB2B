package com.autokartz.autokartz.utils.apiRequestBeans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2/2/2018.
 */

public class SignInBean {


    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    public SignInBean(String email,String pass) {
        this.email=email;
        this.password=pass;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}