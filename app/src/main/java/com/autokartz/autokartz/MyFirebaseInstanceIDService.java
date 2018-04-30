package com.autokartz.autokartz;

import android.util.Log;

import com.autokartz.autokartz.utils.util.Logger;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/*
 * Created by user on 2/14/2018.
 */


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {


    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
       // String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        // Logger.LogDebug(TAG, "Refreshed token: " + refreshedToken);
        //sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        /*OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("FCM_TOKEN", refreshedToken)
                .build();
        Request request = new Request.Builder()
                .url("http://vapi.autokartzinternet.com/api/v1/android/store-token")
                .build();
        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        // TODO: Implement this method to send token to your app server.
    }

}