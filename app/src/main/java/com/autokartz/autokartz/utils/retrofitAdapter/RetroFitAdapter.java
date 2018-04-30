package com.autokartz.autokartz.utils.retrofitAdapter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.autokartz.autokartz.utils.util.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 2/2/2018.
 */

public class RetroFitAdapter {


    private Context context;
    private static final String PREFERENCE_KEY = "user_detail_preference";
    private Toast toast;

    public RetroFitAdapter(Context context) {
        if(context!=null) {
            this.context = context.getApplicationContext();
        }
    }

    public static <object> object createService(Class<object> serviceClass, String baseUrl) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .build();

        Logger.LogDebug("Hello oye",okHttpClient.connectTimeoutMillis()+"");
        Retrofit retrofit=new Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .client(okHttpClient)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();

        return retrofit.create(serviceClass);
        /*if(restAdapter==null) {
            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(30 * 1000, TimeUnit.MILLISECONDS);
            client.setReadTimeout(30 * 1000, TimeUnit.MILLISECONDS);
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(baseUrl)
                    .setClient(new OkClient(client))
                    .build();
        }

        return restAdapter.create(serviceClass);*/
    }


    public void showSuccessMessage(String result) {
        String responseMessage;
        try {
            JSONObject responseJson = new JSONObject(result);
            Boolean isSuccess = responseJson.getBoolean("error");
            if (!isSuccess) {
                JSONObject msgJson = responseJson.getJSONObject("object");
                responseMessage = msgJson.getString("message");
            } else {
                responseMessage = responseJson.getString("message");
            }
            try{ toast.getView().isShown();     // true if visible
                toast.setText(responseMessage);
            } catch (Exception e) {         // invisible if exception
                toast = Toast.makeText(context.getApplicationContext(), responseMessage, Toast.LENGTH_SHORT);
            }
            toast.show();  //finally display it

        } catch (JSONException ex) {
            Log.d("Exception ", ex.getMessage());

        }
    }

}