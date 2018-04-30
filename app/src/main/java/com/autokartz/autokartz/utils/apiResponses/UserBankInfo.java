package com.autokartz.autokartz.utils.apiResponses;

/**
 * Created by autokartz on 16/4/18.
 */

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserBankInfo implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("account_no")
    @Expose
    private String accountNo;
    @SerializedName("ifsc")
    @Expose
    private String ifsc;
    @SerializedName("pan")
    @Expose
    private String pan;
    @SerializedName("gst")
    @Expose
    private String gst;
    @SerializedName("branch")
    @Expose
    private String branch;
    @SerializedName("created_at")
    @Expose
    private Integer createdAt;
    @SerializedName("created_by")
    @Expose
    private Integer createdBy;
    @SerializedName("updated_at")
    @Expose
    private Integer updatedAt;
    @SerializedName("updated_by")
    @Expose
    private Object updatedBy;
    public final static Parcelable.Creator<UserBankInfo> CREATOR = new Creator<UserBankInfo>() {


        @SuppressWarnings({
                "unchecked"
        })
        public UserBankInfo createFromParcel(Parcel in) {
            return new UserBankInfo(in);
        }

        public UserBankInfo[] newArray(int size) {
            return (new UserBankInfo[size]);
        }

    }
            ;
    private final static long serialVersionUID = 6066305863334586148L;

    protected UserBankInfo(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.userId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.accountNo = ((String) in.readValue((String.class.getClassLoader())));
        this.ifsc = ((String) in.readValue((String.class.getClassLoader())));
        this.pan = ((String) in.readValue((String.class.getClassLoader())));
        this.gst = ((String) in.readValue((String.class.getClassLoader())));
        this.branch = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.createdBy = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.updatedAt = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.updatedBy = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    public UserBankInfo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Integer updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(userId);
        dest.writeValue(accountNo);
        dest.writeValue(ifsc);
        dest.writeValue(pan);
        dest.writeValue(gst);
        dest.writeValue(branch);
        dest.writeValue(createdAt);
        dest.writeValue(createdBy);
        dest.writeValue(updatedAt);
        dest.writeValue(updatedBy);
    }

    public int describeContents() {
        return 0;
    }

}