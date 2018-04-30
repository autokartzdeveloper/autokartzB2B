package com.autokartz.autokartz.utils.apiResponses;

import com.autokartz.autokartz.utils.pojoClasses.OrderDetail;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Cortana on 3/14/2018.
 */

public class OrderDetailApiResponse {


    @SerializedName("success")
    private boolean success;
    @SerializedName("result")
    private OrderDetail detail;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public OrderDetail getDetail() {
        return detail;
    }

    public void setDetail(OrderDetail detail) {
        this.detail = detail;
    }

}