package com.autokartz.autokartz.utils.apiRequestBeans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 3/10/2018.
 */

public class PayUMoneyRequestBean {


    @SerializedName("key")
    private String key;
    @SerializedName("salt")
    private String salt;
    @SerializedName("amount")
    private String amount;
    @SerializedName("productinfo")
    private String productinfo;
    @SerializedName("firstname")
    private String firstname;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;

    public PayUMoneyRequestBean(String key, String salt, String amount, String productinfo, String firstname, String email, String phone) {
        this.key = key;
        this.salt = salt;
        this.amount = amount;
        this.productinfo = productinfo;
        this.firstname = firstname;
        this.email = email;
        this.phone = phone;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getProductinfo() {
        return productinfo;
    }

    public void setProductinfo(String productinfo) {
        this.productinfo = productinfo;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
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