package com.autokartz.autokartz.utils.apiResponses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2/21/2018.
 */

public class CategoryPartResultBean {

    @SerializedName("category_id")
    private String catId;
    @SerializedName("subcategory_id")
    private String subCatId;
    @SerializedName("part_id")
    private String partId;
    @SerializedName("category_name")
    private String catName;
    @SerializedName("subcategory_name")
    private String subCatName;
    @SerializedName("part_name")
    private String partName;

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getSubCatId() {
        return subCatId;
    }

    public void setSubCatId(String subCatId) {
        this.subCatId = subCatId;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getSubCatName() {
        return subCatName;
    }

    public void setSubCatName(String subCatName) {
        this.subCatName = subCatName;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

}