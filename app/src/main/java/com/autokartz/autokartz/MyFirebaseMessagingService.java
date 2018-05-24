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
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.autokartz.autokartz.activities.LoginActivity;
import com.autokartz.autokartz.activities.MainDashboardActivity;
import com.autokartz.autokartz.fragments.AutoCashFragment;
import com.autokartz.autokartz.fragments.EnquiryFormFragment;
import com.autokartz.autokartz.fragments.EnquiryFormsFragment;
import com.autokartz.autokartz.fragments.PartSuggestionFragment;
import com.autokartz.autokartz.services.databases.preferences.AccountDetailHolder;
import com.autokartz.autokartz.utils.pojoClasses.CategoryInformation;
import com.autokartz.autokartz.utils.pojoClasses.UserNotificationCount;
import com.autokartz.autokartz.utils.util.Logger;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.apache.poi.hssf.record.formula.functions.Count;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import me.leolin.shortcutbadger.ShortcutBadger;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    String user_id;
    String message;
    private AccountDetailHolder mAccountDetailHolder;
    private Context mContext;
    String enq_id;
    final int NOTIFY_ID = 1; // any integer number
    String count;
    // UserNotificationCount mUserNotificationCount;
    String savedNotificationCount;
    ArrayList<UserNotificationCount> mUserNotificationCount = new ArrayList<>();
    int counter;

    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        mContext = getApplicationContext();
        mAccountDetailHolder = new AccountDetailHolder(mContext);
        mUserNotificationCount = mAccountDetailHolder.getNotificationCount();
        int a = mUserNotificationCount.size();
        Log.v("qwert", String.valueOf(mUserNotificationCount));


        Log.d(TAG, "From: " + remoteMessage.getFrom());
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            count = remoteMessage.getData().get("count");// count
            user_id = remoteMessage.getData().get("user_id");
            enq_id = remoteMessage.getData().get("enq_id");
            message = remoteMessage.getData().get("message");
            if (mUserNotificationCount.size() == 0) {
                UserNotificationCount userNotificationCount = new UserNotificationCount();
                userNotificationCount.setUserNotificationCount(count);
                userNotificationCount.setUserEnquiryId(enq_id);
                userNotificationCount.setUserId(user_id);
                mUserNotificationCount.add(userNotificationCount);
                mAccountDetailHolder.setNotificationCount(mUserNotificationCount);
            } else {
                counter = 0;
                for (int i = 0; i < a; i++) {
                    if (mUserNotificationCount.get(i).getUserEnquiryId().matches(enq_id)) {
                        String count_notification = mUserNotificationCount.get(i).getUserNotificationCount();
                        int savedNotificationINT = Integer.parseInt(count_notification);
                        int countINT = Integer.parseInt(count);
                        int totalNotificationCount = savedNotificationINT + countINT;
                        UserNotificationCount userNotificationCount = new UserNotificationCount();
                        userNotificationCount.setUserNotificationCount(String.valueOf(totalNotificationCount));
                        userNotificationCount.setUserEnquiryId(enq_id);
                        userNotificationCount.setUserId(user_id);
                        mUserNotificationCount.set(i, userNotificationCount);
                        mAccountDetailHolder.setNotificationCount(mUserNotificationCount);

                    } else {
                        if (counter < a) {
                            if (counter == a - 1) {
                                UserNotificationCount userNotificationCount = new UserNotificationCount();
                                userNotificationCount.setUserNotificationCount(count);
                                userNotificationCount.setUserEnquiryId(enq_id);
                                userNotificationCount.setUserId(user_id);
                                mUserNotificationCount.add(userNotificationCount);
                                mAccountDetailHolder.setNotificationCount(mUserNotificationCount);
                            }

                        }
                        counter++;
                    }
                }
            }

        }

        sendNotification(message);

    }


    private void sendNotification(String messageBody) {
        if (mAccountDetailHolder.getIsUserLoggedIn() == true) {
            Intent intent = new Intent(this, MainDashboardActivity.class);
            intent.putExtra("fragment_name", "PartSuggestionFragment");
            intent.putExtra("enquiry_id", enq_id);
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