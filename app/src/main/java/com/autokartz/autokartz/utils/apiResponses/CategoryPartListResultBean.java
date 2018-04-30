package com.autokartz.autokartz.utils.apiResponses;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 * Created by user on 2/21/2018.
 */

public class CategoryPartListResultBean {


    @SerializedName("success")
    private boolean isSuccess;
    @SerializedName("result")
    private ArrayList<CategoryPartResultBean> catPartsList=new ArrayList<>();

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public ArrayList<CategoryPartResultBean> getCatPartsList() {
        return catPartsList;
    }

    public void setCatPartsList(ArrayList<CategoryPartResultBean> catPartsList) {
        this.catPartsList = catPartsList;
    }

}