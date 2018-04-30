package com.autokartz.autokartz.utils.apiRequestBeans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by autokartz on 5/4/18.
 */

public class DisputeDataBean {


    @SerializedName("user_id")
    private String user_id;

    @SerializedName("order_id")
    private String order_id;

    @SerializedName("suggestion_id")
    private  String suggestion_id;

    @SerializedName("dispute_reason")
    private  String dispute_reason;

    @SerializedName("dispute_detail")
    private String dispute_detail;

    public DisputeDataBean(String user_id, String order_id, String suggestion_id, String dispute_reason, String dispute_detail) {
        this.user_id = user_id;
        this.order_id = order_id;
        this.suggestion_id = suggestion_id;
        this.dispute_reason = dispute_reason;
        this.dispute_detail = dispute_detail;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public String getOrderId() {
        return order_id;
    }

    public void setOrderId(String order_id) {
        this.order_id = order_id;
    }

    public String getSuggestionId() {
        return suggestion_id;
    }

    public void setSuggestionId(String suggestion_id) {
        this.suggestion_id = suggestion_id;
    }

    public String getDisputeReason() {
        return dispute_reason;
    }

    public void setDisputereason(String dispute_reason) {
        this.dispute_reason = dispute_reason;
    }

    public String getDisputeDetail() {
        return dispute_detail;
    }

    public void setDisputeDetail(String dispute_detail) {
        this.dispute_detail = dispute_detail;
    }
}
