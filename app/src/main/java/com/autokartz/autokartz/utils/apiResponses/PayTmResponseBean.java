package com.autokartz.autokartz.utils.apiResponses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 3/10/2018.
 */

public class PayTmResponseBean {

    @SerializedName("status")
    private String status;
    @SerializedName("success")
    private boolean isSuccess;
    @SerializedName("result_order_id")
    private String resultOrderId;
    @SerializedName("result_hash")
    private String resultHash;


    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getResultHash() {
        return resultHash;
    }

    public void setResultHash(String resultHash) {
        this.resultHash = resultHash;
    }

    public String getResultOrderId() {
        return resultOrderId;
    }

    public void setResultOrderId(String resultOrderId) {
        this.resultOrderId = resultOrderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}