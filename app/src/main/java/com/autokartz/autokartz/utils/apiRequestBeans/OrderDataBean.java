package com.autokartz.autokartz.utils.apiRequestBeans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Cortana on 3/15/2018.
 */

public class OrderDataBean implements Serializable {

    @SerializedName("user_id")
    private String userId;
    @SerializedName("txn_id")
    private String txnId;
    @SerializedName("amount")
    private String amount;
    @SerializedName("shipping_time")
    private int shipTime;
    @SerializedName("status")
    private String status;
    @SerializedName("product_info")
    private String productInfo;
    @SerializedName("payment_mode")
    private String payment_mode;

    public OrderDataBean(String userId, String txnId, String amount, int shipTime, String status, String productInfo, String paymentMode) {
        this.userId = userId;
        this.txnId = txnId;
        this.amount = amount;
        this.shipTime = shipTime;
        this.status = status;
        this.productInfo = productInfo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getShipTime() {
        return shipTime;
    }

    public void setShipTime(int shipTime) {
        this.shipTime = shipTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public String getPaymentMode() {
        return payment_mode;
    }

    public void setPaymentMode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

}