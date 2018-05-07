package com.autokartz.autokartz;


import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.autokartz.autokartz.activities.LoginActivity;
import com.autokartz.autokartz.activities.MainDashboardActivity;
import com.autokartz.autokartz.fragments.EnquiryFormFragment;
import com.autokartz.autokartz.fragments.EnquiryFormsFragment;
import com.autokartz.autokartz.fragments.PartSuggestionFragment;
import com.autokartz.autokartz.services.databases.preferences.AccountDetailHolder;
import com.autokartz.autokartz.utils.util.Logger;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    String user_id;
    String message;
    private AccountDetailHolder mAccountDetailHolder;
    private Context mContext;
    String enq_id;

    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        mContext = getApplicationContext();
        mAccountDetailHolder = new AccountDetailHolder(mContext);
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        String batchCount = String.valueOf(remoteMessage.getData().size());
        mAccountDetailHolder.setNotificationCount(batchCount);

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            user_id = remoteMessage.getData().get("user_id");
            enq_id = remoteMessage.getData().get("enq_id");
            message = remoteMessage.getData().get("message");
            sendNotification(message);
        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }


    private void sendNotification(String messageBody) {
        if (mAccountDetailHolder.getIsUserLoggedIn() == true) {
            Intent intent = new Intent(this, MainDashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.autokartzlogo)
                            .setContentTitle("Autokartz ")
                            .setContentText(messageBody)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent)
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody));
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());

        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.autokartzlogo)
                            .setContentTitle("Autokartz ")
                            .setContentText(messageBody)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent)
                            .setSound(defaultSoundUri)
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody));
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());

        }
    }


}