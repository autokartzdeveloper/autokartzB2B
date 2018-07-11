package com.autokartz.autokartz.interfaces;

import com.autokartz.autokartz.utils.apiResponses.PayUMoneyResponseBean;

/**
 * Created by user on 3/10/2018.
 */

public interface GetPayUMoneyApiResponseListener {

    void getPayUApiResponse(boolean isSuccess, PayUMoneyResponseBean responseBean);

}