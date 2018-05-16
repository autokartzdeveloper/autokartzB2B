package com.autokartz.autokartz.utils.converter;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.util.TimeZone.getTimeZone;

/**
 * Created by user on 3/19/2018.
 */

public class ConvertDateFormat {


    private static final String CURRENT_DATE_FORMAT = "yyyy-MM-DD";
    private static final String FINAL_DATE_FORMAT = "DD-MMM-yyyy";

    public static String convertDateFormat(String timeStamp) {
        long time = Long.valueOf(timeStamp);
        Date date = new Date(time * 1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        return simpleDateFormat.format(date);
    }

    public static String convertDateTimeFormat(String timeStamp) {
        long time = Long.valueOf(timeStamp);
        Date date = new Date(time * 1000L);
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        return simpleDateFormat.format(date);

    }
}