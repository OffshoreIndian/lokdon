package com.offshoreindian.lokdon.broadcastreceiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.offshoreindian.lokdon.activity.LokdonActivity;
import com.offshoreindian.lokdon.activity.NotifyPopUpActivity;
import com.offshoreindian.lokdon.database.OtherAppEventDataBase;
import com.offshoreindian.lokdon.framework.ApplicationController;
import com.offshoreindian.lokdon.utils.Constants;

/**
 * Created by praveshkumar on 12/10/16.
 */

public class AppLaunchEventReciever extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        OtherAppEventDataBase db = OtherAppEventDataBase.getInstance(context);
        String packageName = intent.getStringExtra(Constants.APP_EVENT_BROADCAST_ACTION_DATA);
        if (!db.isDontAskSelected(packageName)) {
            Intent popUpActivity = new Intent(context, NotifyPopUpActivity.class);
            popUpActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            popUpActivity.putExtra(Constants.IS_APP_EVENT_ACTION_DATA, true);
            popUpActivity.putExtra(Constants.APP_EVENT_BROADCAST_ACTION_DATA, intent.getStringExtra(Constants.APP_EVENT_BROADCAST_ACTION_DATA));
            context.startActivity(popUpActivity);
        } else {
            if (db.isDefaultSelected(packageName)) {
                Intent popUpActivity = new Intent(context, LokdonActivity.class);
                popUpActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                popUpActivity.putExtra(Constants.INTENT_APP_TYPE, Constants.APP_TYPE_MES);
                context.startActivity(popUpActivity);
            }
        }
        //ApplicationController.getInstance().showToastMessages(" Event fire....."+intent.getStringExtra(Constants.APP_EVENT_BROADCAST_ACTION_DATA) );
    }
}
