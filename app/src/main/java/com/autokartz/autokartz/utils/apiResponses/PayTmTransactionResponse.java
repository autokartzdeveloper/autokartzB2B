package com.autokartz.autokartz.utils.apiResponses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 3/10/2018.
 */

public class PayTmTransactionResponse {

    @SerializedName("status")
    private TransactionStatus status;

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

}