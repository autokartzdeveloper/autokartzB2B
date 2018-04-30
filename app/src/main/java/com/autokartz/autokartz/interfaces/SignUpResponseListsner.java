package com.autokartz.autokartz.interfaces;

import com.autokartz.autokartz.utils.apiResponses.UserDetailBean;

/**
 * Created by user on 2/3/2018.
 */

public interface SignUpResponseListsner {

    public void signUpResponse(boolean status, UserDetailBean userDetailBean);

}