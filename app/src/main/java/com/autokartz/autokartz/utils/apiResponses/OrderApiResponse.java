package com.autokartz.autokartz.utils.apiResponses;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 * Created by Cortana on 3/14/2018.
 */

public class OrderApiResponse {


    @SerializedName("success")
    private boolean isSuccess;
    @SerializedName("result")
    private ArrayList<OrderIdResponse> orderIdList;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public ArrayList<OrderIdResponse> getOrderIdList() {
        return orderIdList;
    }

    public void setOrderIdList(ArrayList<OrderIdResponse> orderIdList) {
        this.orderIdList = orderIdList;
    }

}