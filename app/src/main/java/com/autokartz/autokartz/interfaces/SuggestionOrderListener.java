package com.autokartz.autokartz.interfaces;

import com.autokartz.autokartz.utils.apiResponses.SuggestionResponseBean;

/**
 * Created by user on 3/6/2018.
 */

public interface SuggestionOrderListener {

    void updateOrderList(boolean isAdded, SuggestionResponseBean suggestion);

}