package com.autokartz.autokartz.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.services.databases.preferences.AccountDetailHolder;
import com.autokartz.autokartz.utils.util.Logger;
import com.google.firebase.iid.FirebaseInstanceId;

import me.leolin.shortcutbadger.ShortcutBadger;

public class FlashActivity extends AppCompatActivity {
    private static final int DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_flash);
        Logger.LogDebug("hello tken", FirebaseInstanceId.getInstance().getToken());
        init();
        openLoginOrHomeActivity();
    }

    //for notification count
    private void init() {
        int badgeCount = 1;
        ShortcutBadger.applyCount(getApplicationContext(), badgeCount);
    }

    private void openLoginOrHomeActivity() {
        Context mContext = getApplicationContext();
        AccountDetailHolder mAccountDetailHolder = new AccountDetailHolder(mContext);
        final Intent intent;
        if (mAccountDetailHolder.getIsUserLoggedIn()) {
            intent = new Intent(FlashActivity.this, MainDashboardActivity.class);
        } else {
            intent = new Intent(FlashActivity.this, LoginActivity.class);
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

        }, DELAY);
    }
}