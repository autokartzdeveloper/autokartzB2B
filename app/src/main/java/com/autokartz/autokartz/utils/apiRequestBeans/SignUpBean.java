package com.autokartz.autokartz.utils.apiRequestBeans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2/3/2018.
 */

public class SignUpBean {


    @SerializedName("garage_owner_name")
    private String garrageOwnerName;

    @SerializedName("customer_name")
    private String customerName;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("phone")
    private String phone;

    @SerializedName("address")
    private String address;

    @SerializedName("city")
    private String city;

    @SerializedName("state")
    private String state;

    @SerializedName("pin")
    private String pin;

    public SignUpBean(String garrageOwnerName, String customerName, String address, String city, String state, String pin, String email, String password, String phone) {
        this.garrageOwnerName = garrageOwnerName;
        this.customerName = customerName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.pin = pin;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public String getGarrageOwnerName() {
        return garrageOwnerName;
    }

    public void setGarrageOwnerName(String garrageOwnerName) {
        this.garrageOwnerName = garrageOwnerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }


}