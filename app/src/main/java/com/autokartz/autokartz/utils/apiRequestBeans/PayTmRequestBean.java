package com.autokartz.autokartz.utils.apiRequestBeans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 3/10/2018.
 */

public class PayTmRequestBean {


    @SerializedName("MID")
    private String MID;
    @SerializedName("CUST_ID")
    private String CUST_ID;
    @SerializedName("INDUSTRY_TYPE_ID")
    private String INDUSTRY_TYPE_ID;
    @SerializedName("CHANNEL_ID")
    private String CHANNEL_ID;
    @SerializedName("TXN_AMOUNT")
    private String TXN_AMOUNT;
    @SerializedName("WEBSITE")
    private String WEBSITE;
    @SerializedName("M_KEY")
    private String M_KEY;

    public PayTmRequestBean(String MID, String CUST_ID, String INDUSTRY_TYPE_ID, String CHANNEL_ID, String TXN_AMOUNT, String WEBSITE, String M_KEY) {
        this.MID = MID;
        this.CUST_ID = CUST_ID;
        this.INDUSTRY_TYPE_ID = INDUSTRY_TYPE_ID;
        this.CHANNEL_ID = CHANNEL_ID;
        this.TXN_AMOUNT = TXN_AMOUNT;
        this.WEBSITE = WEBSITE;
        this.M_KEY = M_KEY;
    }

    public String getmID() {
        return MID;
    }

    public void setmID(String mID) {
        this.MID = MID;
    }

    public String getCustid() {
        return CUST_ID;
    }

    public void setCustid(String custid) {
        this.CUST_ID = CUST_ID;
    }

    public String getIndustryTypeId() {
        return INDUSTRY_TYPE_ID;
    }

    public void setIndustryTypeId(String industryTypeId) {
        this.INDUSTRY_TYPE_ID = INDUSTRY_TYPE_ID;
    }

    public String getChannelId() {
        return CHANNEL_ID;
    }

    public void setChannelId(String channelId) {
        this.CHANNEL_ID = CHANNEL_ID;
    }

    public String getTxnAmount() {
        return TXN_AMOUNT;
    }

    public void setTxnAmount(String txnAmount) {
        this.TXN_AMOUNT = TXN_AMOUNT;
    }

    public String getWebsite() {
        return WEBSITE;
    }

    public void setWebsite(String website) {
        this.WEBSITE = WEBSITE;
    }

    public String getMerchantKey() {
        return M_KEY;
    }

    public void setMerchantKey(String merchantKey) {
        this.M_KEY = M_KEY;
    }

}