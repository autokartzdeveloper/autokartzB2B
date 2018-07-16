package com.autokartz.autokartz.utils.pojoClasses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Cortana on 1/5/2018.
 */

public class CarInformation implements Serializable {


    @SerializedName("user_id")
    private String mUserId = "";
    @SerializedName("brand")
    private String mBarnd = "";
    @SerializedName("model")
    private String mModel = "";
    @SerializedName("variant")
    private String mVariant = "";
    @SerializedName("engine")
    private String mEnginne = "";
    @SerializedName("year")
    private String mYear = "";
    @SerializedName("car_chassis_number")
    private String mCarChassisNumber = "";
    @SerializedName("partdetails")
    private ArrayList<CategoryInformation> mRequirePartsList = new ArrayList<>();
    @SerializedName("enquiry_id")
    private String mEnquiry = "";
    //new implementaion
    @SerializedName("cname")
    private String mCustomerName = "";
    @SerializedName("caddress")
    private String mCustomerAddress = "";
    @SerializedName("cstate")
    private String mState = "";
    @SerializedName("ccity")
    private String mCity = "";
    @SerializedName("cmobile")
    private String mPhone = "";
    @SerializedName("cemail")
    private String mEmail = "";
    @SerializedName("cpincode")
    private String mPin = "";
    @SerializedName("ccountry")
    private String mCountry = "";

    @SerializedName("type")
    private String mType = "";

    public CarInformation() {
    }

    public CarInformation(String mBarnd, String mModel, String mVariant, String mEnginne, String mYear) {
        this.mBarnd = mBarnd;
        this.mModel = mModel;
        this.mVariant = mVariant;
        this.mEnginne = mEnginne;
        this.mYear = mYear;

    }

    public String getmEnquiry() {
        return mEnquiry;
    }

    public void setmEnquiry(String mEnquiry) {
        this.mEnquiry = mEnquiry;
    }

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public String getmBarnd() {
        return mBarnd;
    }

    public String getmCarChassisNumber() {
        return mCarChassisNumber;
    }

    public String getmModel() {
        return mModel;
    }

    public String getmVariant() {
        return mVariant;
    }

    public String getmEnginne() {
        return mEnginne;
    }

    public String getmYear() {
        return mYear;
    }

    public ArrayList<CategoryInformation> getmRequirePartsList() {
        return mRequirePartsList;
    }

    public void setmBarnd(String mBarnd) {
        this.mBarnd = mBarnd;
    }

    public void setmCarChassisNumber(String mCarChassisNumber) {
        this.mCarChassisNumber = mCarChassisNumber;
    }

    public void setmModel(String mModel) {
        this.mModel = mModel;
    }

    public void setmVariant(String mVariant) {
        this.mVariant = mVariant;
    }

    public void setmEnginne(String mEnginne) {
        this.mEnginne = mEnginne;
    }

    public void setmYear(String mYear) {
        this.mYear = mYear;
    }

    public void setmRequirePartsList(ArrayList<CategoryInformation> mRequirePartsList) {
        this.mRequirePartsList = mRequirePartsList;
    }

    public String getmCustomerName() {
        return mCustomerName;
    }

    public void setmCustomerName(String mCustomerName) {
        this.mCustomerName = mCustomerName;
    }

    public String getmCustomerAddress() {
        return mCustomerAddress;
    }

    public void setmCustomerAddress(String mCustomerAddress) {
        this.mCustomerAddress = mCustomerAddress;
    }

    public String getmState() {
        return mState;
    }

    public void setmState(String mState) {
        this.mState = mState;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPin() {
        return mPin;
    }

    public void setmPin(String mPin) {
        this.mPin = mPin;
    }

    public String getmCountry() {
        return mCountry;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }


}