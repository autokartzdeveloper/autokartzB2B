package com.autokartz.autokartz.interfaces;

import com.autokartz.autokartz.utils.apiResponses.EnquirySuggestionResponseBean;

/**
 * Created by user on 3/5/2018.
 */

public interface SuggestionResponseListener {

    void getSuggestionResponse(EnquirySuggestionResponseBean enquirySuggestionResponseBean, boolean isReceived);

}