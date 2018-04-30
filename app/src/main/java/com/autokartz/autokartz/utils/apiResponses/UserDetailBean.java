package com.autokartz.autokartz.utils.apiResponses;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2/2/2018.
 */

public class UserDetailBean {


    @SerializedName("id")
    private String userId = "";
    @SerializedName("garage_owner_name")
    private String garageOwnerName = "";
    @SerializedName("customer_name")
    private String customerName = "";
    @SerializedName("email")
    private String emailId = "";
    @SerializedName("phone")
    private String phone = "";
    @SerializedName("mobile")
    private String mobile = "";
    @SerializedName("password")
    private String password = "";
    @SerializedName("status")
    private int status;
    @SerializedName("balance")
    private String balance;
    @SerializedName("token")
    private String token;
    @SerializedName("timestamp")
    private String timestamp;
    @SerializedName("address")
    private String address = "";
    @SerializedName("created_at")
    private String created_at = "";


    public String getGarageOwnerName() {
        return garageOwnerName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setGarageOwnerName(String garageOwnerName) {
        this.garageOwnerName = garageOwnerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}