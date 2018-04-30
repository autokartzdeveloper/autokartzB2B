package com.autokartz.autokartz.interfaces;

import com.autokartz.autokartz.utils.apiResponses.UserDetailBean;

/**
 * Created by user on 2/3/2018.
 */

public interface LoginResponseListener {

    void getLoginResponse(boolean status, UserDetailBean userDetailBean);

}