package com.autokartz.autokartz.utils.pojoClasses;

import com.autokartz.autokartz.utils.apiResponses.SuggestionResponseBean;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Cortana on 3/14/2018.
 */

public class OrderDetail implements Serializable {

    @SerializedName("order_id")
    private int orderId;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("txn_id")
    private String txnId;
    @SerializedName("amount")
    private String amount;
    @SerializedName("shipping_time")
    private String shippingTime;
    @SerializedName("product_info")
    private ArrayList<SuggestionResponseBean> productInfo;
    @SerializedName("status")
    private String status;
    @SerializedName("created_at")
    private String timestamp;

    @SerializedName("delivery_status")
    private String deliveryStatus;

    @SerializedName("delivery_create")
    private String deliveryCreated;

    @SerializedName("delivery_update")
    private String deliveryUpdated;

    public int getOrderId() {
        return orderId;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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

    public String getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(String shippingTime) {
        this.shippingTime = shippingTime;
    }

    public ArrayList<SuggestionResponseBean> getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ArrayList<SuggestionResponseBean> productInfo) {
        this.productInfo = productInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDeliveryCreated() {
        return deliveryCreated;
    }

    public void setDeliveryCreated(String timestamp) {
        this.deliveryCreated = deliveryCreated;
    }

    public String getDeliveryUpdated() {
        return deliveryUpdated;
    }

    public void setDeliveryUpdated(String deloveryUpdated) {
        this.deliveryUpdated = deloveryUpdated;
    }

}