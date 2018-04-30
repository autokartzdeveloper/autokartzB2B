package com.autokartz.autokartz.interfaces;

import com.autokartz.autokartz.utils.pojoClasses.OrderDetail;

/**
 * Created by Cortana on 3/14/2018.
 */

public interface GetOrderDetailApiResponseListener {

    public void getOrderDetailResponse(boolean isReceived, OrderDetail orderDetail);

}