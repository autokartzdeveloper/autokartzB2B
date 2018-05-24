package com.autokartz.autokartz.services.databases.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.autokartz.autokartz.utils.apiResponses.UserBankInfo;
import com.autokartz.autokartz.utils.apiResponses.UserDetailBean;
import com.autokartz.autokartz.utils.pojoClasses.CategoryInformation;
import com.autokartz.autokartz.utils.pojoClasses.UserNotificationCount;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by user on 1/3/2018.
 */

public class AccountDetailHolder {

    private static SharedPreferences sharedPreferences;
    private static final String PREFERENCE_KEY = "user_detail_preference";
    private static final String KEY_SELECTED_PARTS = "key_selected_parts";
    private static final String KEY_USER_DETAIL_BEAN = "userDetailBean";
    private static final String NOTIFICATION_COUNT = "notifiactionCount";
    private static final String NOTIFICATION_COUNT_ENQUIRYFORM = "notifiactionCountenquiryform";
    private static final String NOTIFICATION_COUNT_ENQUIRY = "notifiactionCountEnquiry";
    private static final String KEY_USER_BANK_DETAIL_BEAN = "userBankDetailBean";
    private static final String KEY_ABC = "ABC";
    private static final String KEY_IS_USER__LOGGED_IN = "isUserLoggedIn";
    private static final String KEY_IS_CAR_INFO_LOADED = "isCarInfoLoaded";
    private static final String KEY_IS_CAT_INFO_LOADED = "isCatInfoLoaded";
    private static final String KEY_ADD_PART_LIST = "addPartList";

    public static SharedPreferences.Editor editor;

    public AccountDetailHolder(Context context) {
        try {
            sharedPreferences = context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void setKeyIsCarInfoLoaded(boolean isLoaded) {
        sharedPreferences.edit().putBoolean(KEY_IS_CAR_INFO_LOADED, isLoaded).apply();
    }

    public void setKeyIsCatInfoLoaded(boolean isLoaded) {
        sharedPreferences.edit().putBoolean(KEY_IS_CAT_INFO_LOADED, isLoaded).apply();
    }

    public boolean getIsCarInfoLoaded() {
        return sharedPreferences.getBoolean(KEY_IS_CAR_INFO_LOADED, false);
    }

    public boolean getIsCatInfoLoaded() {
        return sharedPreferences.getBoolean(KEY_IS_CAT_INFO_LOADED, false);
    }

    public static void setSharedPreference(String preference, String value, Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
        editor.putString(preference, value);
        editor.apply();
    }

    public void setSelectedCarParts(ArrayList<CategoryInformation> selectedCarParts) {
        Gson gson = new Gson();
        String jsonDetails = gson.toJson(selectedCarParts);
        sharedPreferences.edit().putString(KEY_SELECTED_PARTS, jsonDetails).apply();
    }

    public ArrayList<CategoryInformation> getSelectedCarParts() {
        Gson gson = new Gson();
        String jsonDetails = sharedPreferences.getString(KEY_SELECTED_PARTS, null);
        Type type = new TypeToken<ArrayList<CategoryInformation>>() {
        }.getType();
        ArrayList<CategoryInformation> list = gson.fromJson(jsonDetails, type);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public void setAddPart(ArrayList<CategoryInformation> catInfoAddPartList) {
        Gson gson = new Gson();
        String jsonDetails = gson.toJson(catInfoAddPartList);
        sharedPreferences.edit().putString(KEY_ADD_PART_LIST, jsonDetails).apply();
    }

    public ArrayList<CategoryInformation> getAddPartDetails() {
        Gson gson = new Gson();
        String jsonDetails = sharedPreferences.getString(KEY_ADD_PART_LIST, null);
        Type type = new TypeToken<ArrayList<CategoryInformation>>() {
        }.getType();
        ArrayList<CategoryInformation> list = gson.fromJson(jsonDetails, type);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public void setUserDetailBean(UserDetailBean userDetailBean) {
        Gson gson = new Gson();
        String json = gson.toJson(userDetailBean);
        sharedPreferences.edit().putString(KEY_USER_DETAIL_BEAN, json).apply();
    }

    public void setUserBankDetailBean(UserBankInfo userBankInfo) {
        Gson gson = new Gson();
        String json = gson.toJson(userBankInfo);
        sharedPreferences.edit().putString(KEY_USER_BANK_DETAIL_BEAN, json).apply();
    }

    public UserDetailBean getUserDetailBean() {
        Gson gson = new Gson();
        String defaultJson = gson.toJson(new UserDetailBean());
        String json = sharedPreferences.getString(KEY_USER_DETAIL_BEAN, defaultJson);
        UserDetailBean userDetailBean = gson.fromJson(json, UserDetailBean.class);
        if (userDetailBean == null) {
            userDetailBean = new UserDetailBean();
        }
        return userDetailBean;
    }

    public void setNotificationCount(ArrayList<UserNotificationCount> userNotificationCount) {
        Gson gson = new Gson();
        String json = gson.toJson(userNotificationCount);
        sharedPreferences.edit().putString(NOTIFICATION_COUNT, json).apply();
    }


    public ArrayList<UserNotificationCount> getNotificationCount() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(NOTIFICATION_COUNT, null);
        Type type = new TypeToken<ArrayList<UserNotificationCount>>() {
        }.getType();
        ArrayList<UserNotificationCount> list = gson.fromJson(json, type);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public UserBankInfo getUserBankDetailBean() {
        Gson gson = new Gson();
        String defaultJson = gson.toJson(new UserBankInfo());
        String json = sharedPreferences.getString(KEY_USER_BANK_DETAIL_BEAN, defaultJson);
        UserBankInfo userBankInfo = gson.fromJson(json, UserBankInfo.class);
        if (userBankInfo == null) {
            userBankInfo = new UserBankInfo();
        }
        return userBankInfo;
    }

    public void setUserLoggedIn(boolean status) {
        sharedPreferences.edit().putBoolean(KEY_IS_USER__LOGGED_IN, status).apply();
    }

    public boolean getIsUserLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_USER__LOGGED_IN, false);
    }

    public static boolean setLoginSharedPreference(String preference, Boolean value, Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
        editor.putBoolean(preference, value);
        editor.apply();
        return value;
    }

    public static boolean getLoginSharedPreference(String preference, Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(preference, false);
    }

    public static String getSharedPreference(String preference, Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(preference, null);
    }

    public void clearAllSharedPreferences() {
        sharedPreferences.edit().remove(KEY_SELECTED_PARTS).apply();
        sharedPreferences.edit().remove(KEY_USER_DETAIL_BEAN).apply();
        sharedPreferences.edit().remove(KEY_IS_USER__LOGGED_IN).apply();

    }

    public void deleteNotificationCount() {
        sharedPreferences.edit().remove(NOTIFICATION_COUNT).apply();
    }
}