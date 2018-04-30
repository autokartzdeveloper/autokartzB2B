package com.autokartz.autokartz.utils.pojoClasses;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Cortana on 1/5/2018.
 */

public class CarInformation implements Serializable {


    @SerializedName("user_id")
    private String mUserId="";
    @SerializedName("brand")
    private String mBarnd="";
    @SerializedName("model")
    private String mModel="";
    @SerializedName("variant")
    private String mVariant="";
    @SerializedName("engine")
    private String mEnginne="";
    @SerializedName("year")
    private String mYear="";
    @SerializedName("car_chassis_number")
    private String mCarChassisNumber="";
    @SerializedName("partdetails")
    private ArrayList<CategoryInformation> mRequirePartsList=new ArrayList<>();

    public CarInformation() {}

    public CarInformation(String mBarnd, String mModel, String mVariant, String mEnginne, String mYear) {
        this.mBarnd = mBarnd;
        this.mModel = mModel;
        this.mVariant = mVariant;
        this.mEnginne = mEnginne;
        this.mYear = mYear;
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

}