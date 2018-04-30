package com.autokartz.autokartz.utils.pojoClasses;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Cortana on 1/13/2018.
 */

public class CategoryInformation implements Serializable {

    private String mCatId="";
    private String mCatName="";
    private String mSubCatId="";
    private String mSubCatName="";
    @SerializedName("part_id")
    private String mPartId="";
    @SerializedName("agent_remark")
    private String agentRemark="";
    @SerializedName("part_name")
    private String mPartName="";
    @SerializedName("requirement_detail")
    private String mProductDetail="";
    @SerializedName("partimage")
    private ArrayList<String> mImagePathList=new ArrayList<>();
    @SerializedName("is_defined")
    private int isDefined=1;
    private int imageResId;

    public CategoryInformation(String catId, String mCatName,String subCatId, String mSubCatName,String partId, String mPartName,int isDefined) {
        this.mCatId=catId;
        this.mCatName = mCatName;
        this.mSubCatId=subCatId;
        this.mSubCatName = mSubCatName;
        this.mPartId=partId;
        this.mPartName = mPartName;
        this.isDefined=isDefined;
    }

    public CategoryInformation() {

    }

    public String getmCatName() {
        return mCatName;
    }

    public String getmPartName() {
        return mPartName;
    }

    public String getmSubCatName() {
        return mSubCatName;
    }

    public ArrayList<String> getmImagePathList() {
        return mImagePathList;
    }

    public String getmProductDetail() {
        return mProductDetail;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setmCatName(String mCatName) {
        this.mCatName = mCatName;
    }

    public void setmPartName(String mPartName) {
        this.mPartName = mPartName;
    }

    public void setmSubCatName(String mSubCatName) {
        this.mSubCatName = mSubCatName;
    }

    public String getAgentRemark() {
        return agentRemark;
    }

    public void setAgentRemark(String agentRemark) {
        this.agentRemark = agentRemark;
    }

    public void setmImagePathList(ArrayList<String> mImagePathList) {
        this.mImagePathList = mImagePathList;
    }

    public void setmProductDetail(String mProductDetail) {
        this.mProductDetail = mProductDetail;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual=false;
        if(obj!=null && obj instanceof CategoryInformation ) {
            isEqual=(this.mPartName.equalsIgnoreCase( ((CategoryInformation) obj).getmPartName()));
        }
        return isEqual;
    }

    public String getmCatId() {
        return mCatId;
    }

    public void setmCatId(String mCatId) {
        this.mCatId = mCatId;
    }

    public String getmSubCatId() {
        return mSubCatId;
    }

    public void setmSubCatId(String mSubCatId) {
        this.mSubCatId = mSubCatId;
    }

    public String getmPartId() {
        return mPartId;
    }

    public void setmPartId(String mPartId) {
        this.mPartId = mPartId;
    }

    public int isDefined() {
        return isDefined;
    }

    public void setDefined(int defined) {
        isDefined = defined;
    }

}