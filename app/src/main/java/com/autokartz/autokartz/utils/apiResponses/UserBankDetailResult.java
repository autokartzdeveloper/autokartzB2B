package com.autokartz.autokartz.utils.apiResponses;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserBankDetailResult implements Serializable, Parcelable {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private UserBankInfo userBankInfo;
    public final static Parcelable.Creator<UserBankDetailResult> CREATOR = new Creator<UserBankDetailResult>() {


        @SuppressWarnings({
                "unchecked"
        })
        public UserBankDetailResult createFromParcel(Parcel in) {
            return new UserBankDetailResult(in);
        }

        public UserBankDetailResult[] newArray(int size) {
            return (new UserBankDetailResult[size]);
        }

    };
    private final static long serialVersionUID = -8120089328677040867L;

    protected UserBankDetailResult(Parcel in) {
        this.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.userBankInfo = ((UserBankInfo) in.readValue((UserBankInfo.class.getClassLoader())));
    }

    public UserBankDetailResult() {
    }

    public boolean isSuccess() {
        return success;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public UserBankInfo getUserBankInfo() {
        return userBankInfo;
    }

    public void setUserBankInfo(UserBankInfo userBankInfo) {
        this.userBankInfo = userBankInfo;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(success);
        dest.writeValue(userBankInfo);
    }

    public int describeContents() {
        return 0;
    }

}
