package com.mnpost.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.firebase.messaging.FirebaseMessaging;
import com.mnpost.app.data.UserInfo;
import com.mnpost.app.util.Const;
import com.mnpost.app.util.NotificationUtils;

public class BaseActivity extends AppCompatActivity implements BaseContract{

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private android.support.v7.app.AlertDialog.Builder dNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserInfo.reset();
    }


    @Override
    public void createToolBar(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); ActionBar ab = getSupportActionBar();
        ab.setTitle(title);

        /*
         toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
         */
        fireBaseBroadcast();
    }



    private void fireBaseBroadcast() {
        // show dialog notification
        dNotification = new android.support.v7.app.AlertDialog.Builder(BaseActivity.this);
        dNotification.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // checking for type intent filter
                if (intent.getAction().equals(Const.REGISTRATION_COMPLETE)) {
                    FirebaseMessaging.getInstance().subscribeToTopic(Const.TOPIC_GLOBAL);
                } else if (intent.getAction().equals(Const.PUSH_NOTIFICATION)) {
                    String message = intent.getStringExtra("message");
                    String title = intent.getStringExtra("title");
                    showNotification(title, message);
                }
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Const.REGISTRATION_COMPLETE));

        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Const.PUSH_NOTIFICATION));

        NotificationUtils.clearNotifications(getApplicationContext());

    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);

        super.onPause();
    }

    private void showNotification(String title, String messenge) {

        dNotification.setTitle(title);
        dNotification.setMessage(messenge);
        dNotification.show();
    }

}
