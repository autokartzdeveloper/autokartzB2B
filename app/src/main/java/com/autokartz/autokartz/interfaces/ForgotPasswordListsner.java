package com.autokartz.autokartz.interfaces;

import com.autokartz.autokartz.utils.apiResponses.ForgotPasswordBeanResponse;

/**
 * Created by user on 2/3/2018.
 */

public interface ForgotPasswordListsner {
    void forgotPasswordListsner(boolean success, ForgotPasswordBeanResponse forgotPasswordBeanResponse);


}