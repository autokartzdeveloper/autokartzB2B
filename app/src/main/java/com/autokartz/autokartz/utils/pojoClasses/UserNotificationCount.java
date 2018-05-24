package com.autokartz.autokartz.utils.pojoClasses;

/**
 * Created by Cortana on 1/8/2018.
 */

public class UserNotificationCount {

    private String userNotificationCount = "";
    private String userId = "";
    private String userEnquiryId = "";

   /* public UserNotificationCount(String count, String userid, String enquiryid) {
        userNotificationCount = count;
        userId = userid;
        userEnquiryId = enquiryid;

    }*/

    public void setUserNotificationCount(String count) {
        this.userNotificationCount = count;
    }

    public String getUserNotificationCount() {
        return userNotificationCount;
    }

    public void setUserEnquiryId(String userenquiryid) {
        this.userEnquiryId = userenquiryid;
    }

    public String getUserEnquiryId() {
        return userEnquiryId;
    }

    public void setUserId(String userid) {
        this.userId = userid;
    }

    public String getUserId() {
        return userId;
    }


}