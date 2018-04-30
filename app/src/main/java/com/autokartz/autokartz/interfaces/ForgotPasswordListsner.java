package com.autokartz.autokartz.interfaces;

import com.autokartz.autokartz.utils.apiResponses.ForgotPasswordBeanResponse;

/**
 * Created by user on 2/3/2018.
 */

public interface ForgotPasswordListsner {
    public void forgotPasswordListsner(boolean success, ForgotPasswordBeanResponse forgotPasswordBeanResponse);


}