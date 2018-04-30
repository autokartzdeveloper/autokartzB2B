package com.autokartz.autokartz.interfaces;

import com.autokartz.autokartz.utils.apiResponses.PayTmResponseBean;

/**
 * Created by user on 3/10/2018.
 */

public interface GetPayTMApiResponseListener {

    public void getPayTmApiResponse(boolean isSuccess, PayTmResponseBean responseBean);

}