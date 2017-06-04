package com.offshoreindian.lokdon.services;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.offshoreindian.lokdon.utils.DebugUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class UStats {


    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");

    public static final String TAG = UStats.class.getSimpleName();
    @SuppressWarnings("ResourceType")
    public static void getStats(Context context){
        UsageStatsManager usm = (UsageStatsManager) context.getSystemService("usagestats");
       // int interval = UsageStatsManager.INTERVAL_YEARLY;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("IST"));

//        long endTime = calendar.getTimeInMillis()-2000;
//        calendar.add(Calendar.MINUTE, -1);
//
//        long startTime = calendar.getTimeInMillis();


        long endTime = System.currentTimeMillis();

        long startTime =System.currentTimeMillis() -(60*1000);


        DebugUtil.printLog(" start time  :: "+startTime +"   endtime   :: "+endTime);

        //Log.d(TAG, "Range start:" + dateFormat.format(startTime) );
        //Log.d(TAG, "Range end:" + dateFormat.format(endTime));
        //if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
        {
            UsageEvents uEvents = usm.queryEvents(startTime,endTime);
            while (uEvents.hasNextEvent()){
                UsageEvents.Event e = new UsageEvents.Event();
                uEvents.getNextEvent(e);
                DebugUtil.printLog(" event :: "+e.getPackageName());

            }
        }

    }

    public static List<UsageStats> getUsageStatsList(Context context){
        UsageStatsManager usm = getUsageStatsManager(context);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("IST"));

        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.HOUR, -1);

        long startTime = calendar.getTimeInMillis();

         List<UsageStats> usageStatsList = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            usageStatsList = usm.queryUsageStats(UsageStatsManager.INTERVAL_BEST, startTime, endTime);
        }
        return usageStatsList;
    }

    public static void printUsageStats(List<UsageStats> usageStatsList){
        for (UsageStats u : usageStatsList){

            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){

            }

        }

    }

    public static void printCurrentUsageStatus(Context context){
        printUsageStats(getUsageStatsList(context));
    }
    @SuppressWarnings("ResourceType")
    private static UsageStatsManager getUsageStatsManager(Context context){
        UsageStatsManager usm = (UsageStatsManager) context.getSystemService("usagestats");
        return usm;
    }
}
