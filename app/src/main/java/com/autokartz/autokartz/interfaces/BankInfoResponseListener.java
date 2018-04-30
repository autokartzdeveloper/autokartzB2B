package com.autokartz.autokartz.interfaces;

import com.autokartz.autokartz.utils.apiResponses.UserBankInfo;
import com.autokartz.autokartz.utils.apiResponses.UserDetailBean;

/**
 * Created by user on 2/3/2018.
 */

public interface BankInfoResponseListener {

    void getBankInfoResponse(boolean success, UserBankInfo userBankInfo);

}