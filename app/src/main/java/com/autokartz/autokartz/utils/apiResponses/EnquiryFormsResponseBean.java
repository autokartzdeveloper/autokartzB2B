package com.autokartz.autokartz.utils.apiResponses;

import com.autokartz.autokartz.utils.converter.ConvertDateFormat;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2/26/2018.
 */

public class EnquiryFormsResponseBean {


    @SerializedName("id")
    private String enquiry_id;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("brand")
    private String brand;
    @SerializedName("model")
    private String model;

    public String getEnquiry_id() {
        return enquiry_id;
    }

    public void setEnquiry_id(String enquiry_id) {
        this.enquiry_id = enquiry_id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        ConvertDateFormat.convertDateFormat(createdAt);
        this.createdAt = createdAt;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {

        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {

        this.model = model;
    }

}