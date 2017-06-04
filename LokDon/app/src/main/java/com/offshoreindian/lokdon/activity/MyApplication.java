package com.offshoreindian.lokdon.activity;

import android.app.Application;
import android.support.multidex.MultiDex;


/**
 * Created by praveshkumar on 06/10/16.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);


        // Initialize the SDK before executing any other operations,
        //FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this);
    }
}
