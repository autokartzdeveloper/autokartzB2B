package com.autokartz.autokartz.utils.apiResponses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 3/10/2018.
 */

public class PayUMoneyResponseBean {


    @SerializedName("success")
    private boolean isSuccess;
    @SerializedName("result_hash")
    private String resultHash;
    @SerializedName("result_txnid")
    private String resultTxnId;

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

    public String getResultTxnId() {
        return resultTxnId;
    }

    public void setResultTxnId(String resultTxnId) {
        this.resultTxnId = resultTxnId;
    }

}