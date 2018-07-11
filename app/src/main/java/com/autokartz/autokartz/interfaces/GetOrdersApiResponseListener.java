package com.autokartz.autokartz.interfaces;

import com.autokartz.autokartz.utils.apiResponses.OrderIdResponse;

import java.util.ArrayList;

/**
 * Created by Cortana on 3/14/2018.
 */

public interface GetOrdersApiResponseListener {

    void getOrderResponse(boolean isReceived, ArrayList<OrderIdResponse> orderList);

}