package com.autokartz.autokartz.utils.util.constants;

import com.autokartz.autokartz.BuildConfig;

/**
 * Created by Cortana on 1/11/2018.
 */

public class AppConfig {


    private static final String AUTOKARTZ_PRODUCTION_URL="http://vapi.autokartzinternet.com/api/v1/";
    private static final String AUTOKARTZ_STAGING_URL="http://vapi.autokartzinternet.com/api/v1/";
    public static String APP_SERVER_URL="http://vapi.autokartzinternet.com/api/v1/";

    public static void setConfig() {
        if(BuildConfig.DEBUG) {
            APP_SERVER_URL=AUTOKARTZ_PRODUCTION_URL;
        } else {
            APP_SERVER_URL=AUTOKARTZ_STAGING_URL;
        }
    }

}