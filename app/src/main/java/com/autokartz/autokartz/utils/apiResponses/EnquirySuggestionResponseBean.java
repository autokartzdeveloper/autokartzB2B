package com.autokartz.autokartz.utils.apiResponses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by user on 3/5/2018.
 */

public class EnquirySuggestionResponseBean {


    @SerializedName("id")
    private String enquiryId;
    @SerializedName("car_chassis_number")
    private String carChassisNumber;
    @SerializedName("brand")
    private String brand;
    @SerializedName("model")
    private String model;
    @SerializedName("variant")
    private String variant;
    @SerializedName("engine")
    private String engine;
    @SerializedName("year")
    private String year;

    @SerializedName("cod_status")
    private String codStatus;
    @SerializedName("part_details")
    private ArrayList<EnquiryPartSuggestionResponseBean> mEnquirySuggestionPartResponseBeanList = new ArrayList<>();

    public String getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(String enquiryId) {
        this.enquiryId = enquiryId;
    }

    public String getCarChassisNumber() {
        return carChassisNumber;
    }

    public void setCarChassisNumber(String carChassisNumber) {
        this.carChassisNumber = carChassisNumber;
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

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getCodStatus() {
        return codStatus;
    }

    public void setCodStatus(String codStatus) {
        this.codStatus = codStatus;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


    public ArrayList<EnquiryPartSuggestionResponseBean> getmEnquirySuggestionPartResponseBeanList() {
        return mEnquirySuggestionPartResponseBeanList;
    }

    public void setmEnquirySuggestionPartResponseBeanList(ArrayList<EnquiryPartSuggestionResponseBean> mEnquirySuggestionPartResponseBean) {
        this.mEnquirySuggestionPartResponseBeanList = mEnquirySuggestionPartResponseBean;
    }

}