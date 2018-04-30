package com.autokartz.autokartz.utils.apiResponses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 3/1/2018.
 */

public class SuggestionResponseBean {


    @SerializedName("id")
    private String id;
    @SerializedName("enquiry_product_id")
    private String enquiry_product_id;
    @SerializedName("brand")
    private String brand;
    @SerializedName("quality")
    private String quality;
    @SerializedName("availability")
    private String availability;
    @SerializedName("shipping_time")
    private String shipTime;
    @SerializedName("shipping_charges")
    private String shipCharges;
    @SerializedName("tax")
    private String tax;
    @SerializedName("warranty")
    private String warranty;
    @SerializedName("vendor_price")
    private String price;
    @SerializedName("part_name")
    private String partName;
    @SerializedName("part_url")
    private String partUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnquiry_product_id() {
        return enquiry_product_id;
    }

    public void setEnquiry_product_id(String enquiry_product_id) {
        this.enquiry_product_id = enquiry_product_id;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getShipTime() {
        return shipTime;
    }

    public void setShipTime(String shipTime) {
        this.shipTime = shipTime;
    }

    public String getShipCharges() {
        return shipCharges;
    }

    public void setShipCharges(String shipCharges) {
        this.shipCharges = shipCharges;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPartUrl() {
        return partUrl;
    }

    public void setPartUrl(String partUrl) {
        this.partUrl = partUrl;
    }


    @Override
    public boolean equals(Object obj) {
        boolean isEqual=false;
        if(obj!=null && obj instanceof SuggestionResponseBean ) {
            isEqual=(this.id.equalsIgnoreCase( ((SuggestionResponseBean) obj).getId()));
        }
        return isEqual;
    }

}