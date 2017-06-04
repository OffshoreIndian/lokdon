package com.offshoreindian.lokdon.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.app.ActivityManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.telephony.TelephonyManager;


import com.offshoreindian.lokdon.activity.LokdonActivity;
import com.offshoreindian.lokdon.activity.NotifyPopUpActivity;
import com.offshoreindian.lokdon.database.OtherAppEventDataBase;
import com.offshoreindian.lokdon.utils.Constants;
import com.offshoreindian.lokdon.utils.DebugUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;

/**
 * Created by praveshkumar on 11/10/16.
 */


public class AppTrackingService extends Service {


    ActivityManager.RunningAppProcessInfo aTask = null;

    List<ActivityManager.RunningAppProcessInfo> alltasks = null;
    PackageManager packageManager = null;
    ActivityManager activityManager;
    long startTime;
    private long endTime;
    TimerTask timerTask = null;
    TelephonyManager telephony = null;
    ApplicationInfo applicationInfo = null;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    Handler handler;
    boolean isScreenOn = true;
    private String runningAppPackageName = null;
    private String previousAppPackageName = null;

    private class ScreenOnOffReciever extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                //  Log.i("Check", "Screen went OFF");
                isScreenOn = false;
                // Toast.makeText(context, "screen OFF",Toast.LENGTH_LONG).show();
            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                //Log.i("Check","Screen went ON");
                isScreenOn = true;
                ;
                // Toast.makeText(context, "screen ON",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        DebugUtil.printLog("onCreate ");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DebugUtil.printLog("onStartCommand ");
        this.context = this;

//        if(!Constant.isSameParent(context.getPackageName())) {
//
//            Toast.makeText(context," Not Same Package Name "+context.getPackageName(),Toast.LENGTH_LONG).show();
//            return START_NOT_STICKY;
//
//            //context.startService(new Intent(context, BackGroundTimerServices.class));
//        }
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//        {
//             return START_NOT_STICKY;
//        }
        try {
            packageManager = this.getPackageManager();
            activityManager = (ActivityManager) this
                    .getSystemService(Context.ACTIVITY_SERVICE);
        } catch (Exception e) {
            DebugUtil.printExceptionLog("onStartCommand  :: ", e);
        }


        handler = new Handler();


        new Timer().schedule(startTimerTask(), 3000, 3000);


        try {
            final IntentFilter screenOffFilter = new IntentFilter();
            screenOffFilter.addAction(Intent.ACTION_SCREEN_ON);
            screenOffFilter.addAction(Intent.ACTION_SCREEN_OFF);
            registerReceiver(new ScreenOnOffReciever(), screenOffFilter);

        } catch (Exception e) {
            DebugUtil.printExceptionLog("onStartCommand  :: ", e);
        }

        return START_STICKY;
    }


    private TimerTask startTimerTask() {


        timerTask = new TimerTask() {
            public void run() {

                handler.post(new Runnable() {
                    public void run() {

                        try {
                            if (isScreenOn) {
                                handleRunningProcess();
//                                if(backGroundApp !=null)
//                                {
//                                    // do some thing
//                                    sendDataToServerForProcess();
//                                }
//                                backGroundApp = null;
                            } else {


                            }
                        } catch (Exception e) {
                            DebugUtil.printExceptionLog("onStartCommand  :: ", e);
                        }


                    }

                });
            }
        };

        return timerTask;

    }

    public String getDate(long milliSeconds) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    private void handleRunningProcess() {

        // DebugUtil.printLog("handleRunningProcess handleRunningProcess");
        //alltasks.get(0).processName;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {


            UsageStatsManager usm = (UsageStatsManager) context.getSystemService("usagestats");
            endTime = System.currentTimeMillis();

            startTime = System.currentTimeMillis() - (60 * 1000);
            UsageEvents uEvents = usm.queryEvents(startTime, endTime);
            UsageEvents.Event e = null;
            while (uEvents.hasNextEvent()) {
                e = new UsageEvents.Event();
                uEvents.getNextEvent(e);


            }
            if (e != null) {
                //runningAppPackageName = e.getPackageName();
                checkPackageName(e.getPackageName());


                // DebugUtil.printLog("  Tracking service package name  :::  "+ runningAppPackageName +" time "+getDate(System.currentTimeMillis()));
            }

        } else {
            alltasks = activityManager.getRunningAppProcesses();
            aTask = alltasks.get(0);
            DebugUtil.printLog(" Running App ::: " + alltasks.get(0).processName);
            // runningAppPackageName = alltasks.get(0).processName;
            checkPackageName(alltasks.get(0).processName);


        }


    }


    /**
     * Here we check for running packege we have to show the pop up
     */
    private void checkPackageName(String packageName) {
       // DebugUtil.printLog(" pag ::  " + packageName);
        boolean isPackageMatch = false;
        runningAppPackageName = packageName;
      //  DebugUtil.printLog(previousAppPackageName +" context.getPackageName() "+context.getPackageName());
        if(previousAppPackageName !=null && previousAppPackageName.equalsIgnoreCase(context.getPackageName()))
        {
            previousAppPackageName = packageName;
            return;
        }

        if (previousAppPackageName != null) {

            if (!runningAppPackageName.equalsIgnoreCase(previousAppPackageName)) {
                for (int i = 0; i < Constants.suppotedAppPackageName.length; i++) {
                    if (runningAppPackageName.equalsIgnoreCase(Constants.suppotedAppPackageName[i])) {
                        // need to show the pop up use LokDon app


                        OtherAppEventDataBase db =   OtherAppEventDataBase.getInstance(context);
                        boolean flag = db.ifExists(runningAppPackageName);
                        DebugUtil.printLog(" flag "+flag);
                        if(!flag)
                        {
                            db.insertAppInfo("temp ",runningAppPackageName);
                        }



                      //  DebugUtil.printLog(previousAppPackageName + " 11111 Package match " + runningAppPackageName);
                        isPackageMatch = true;
                        previousAppPackageName = packageName;
                       // if(!db.isDontAskSelected(runningAppPackageName))
                        {
                            Intent broadcastIntent = new Intent();
                            broadcastIntent.setAction(Constants.APP_EVENT_BROADCAST_ACTION);
                            broadcastIntent.putExtra(Constants.APP_EVENT_BROADCAST_ACTION_DATA, runningAppPackageName);
                            context.sendBroadcast(broadcastIntent);
                        }


                        break;
                    }

                }

                if (!isPackageMatch) {
                    previousAppPackageName = packageName;
                }
            }
            previousAppPackageName = packageName;

        } else {
//            for (int i=0;i < Constants.suppotedAppPackageName.length;i++)
//            {
//                if(runningAppPackageName.equalsIgnoreCase(Constants.suppotedAppPackageName[i]))
//                {
//                    // need to show the pop up use LokDon app
//                    DebugUtil.printLog(previousAppPackageName+" Package match "+packageName);
//                    isPackageMatch = true;
//                    previousAppPackageName = packageName;
//                    break;
//                }
//
//            }
//
//            if(!isPackageMatch)
            {
                previousAppPackageName = packageName;
            }
        }
    }

    public String getAppName(PackageManager pm, String packageName) {


        try {
            applicationInfo = pm.getApplicationInfo(packageName, 0);
        } catch (final PackageManager.NameNotFoundException e) {
            applicationInfo = null;
            DebugUtil.printExceptionLog("onStartCommand  :: ", e);

        }
        final String applicationName = (String) (applicationInfo != null ? pm.getApplicationLabel(applicationInfo) : "(unknown)");

        return applicationName;
    }
}













