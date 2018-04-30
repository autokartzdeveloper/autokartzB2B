package com.autokartz.autokartz.utils.apiResponses;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 * Created by user on 2/26/2018.
 */

public class EnquiryFormsListResponseBean {


    @SerializedName("success")
    private boolean isSucess;
    @SerializedName("result")
    private ArrayList<EnquiryFormsResponseBean> enquiryFormsList=new ArrayList<>();

    public boolean isSucess() {
        return isSucess;
    }

    public void setSucess(boolean sucess) {
        isSucess = sucess;
    }

    public ArrayList<EnquiryFormsResponseBean> getEnquiryFormsList() {
        return enquiryFormsList;
    }

    public void setEnquiryFormsList(ArrayList<EnquiryFormsResponseBean> enquiryFormsList) {
        this.enquiryFormsList = enquiryFormsList;
    }

}