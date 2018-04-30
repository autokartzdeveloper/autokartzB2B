package com.autokartz.autokartz.utils.pojoClasses;

/**
 * Created by Cortana on 1/8/2018.
 */

public class UserDetails {

    private String userName="";
    private String userPassword="";
    private String userEmail="";
    private String userMobile="";

    public UserDetails(){}

    public UserDetails(String name, String pass, String email, String mobile) {
        userName=name;
        userPassword=pass;
        userEmail=email;
        userMobile=mobile;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

}