package com.offshoreindian.lokdon.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.database.OtherAppEventDataBase;
import com.offshoreindian.lokdon.framework.ApplicationController;
import com.offshoreindian.lokdon.utils.Constants;
import com.offshoreindian.lokdon.utils.DebugUtil;

/**
 * Created by praveshkumar on 12/10/16.
 */

public class NotifyPopUpActivity extends Activity {
    private Dialog dialog;
    private Button okButton, cancelButton;
    private TextView messageTextview;
    private ApplicationInfo applicationInfo;
    private PackageManager packageManager;
    private CheckBox donot_ask;
    private boolean do_not_ask = false;
    private ImageView crossImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (dialog == null) {
            dialog = new Dialog(this, R.style.Dialog_theme);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.app_notify_popup);
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            messageTextview = (TextView) dialog.findViewById(R.id.message_box);
            messageTextview.setText("Used lokdon to send your message.");

            donot_ask = (CheckBox) dialog.findViewById(R.id.donot_ask);
            donot_ask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        do_not_ask = true;
                        OtherAppEventDataBase.getInstance(NotifyPopUpActivity.this).insertDoAskStatus(getIntent().getStringExtra(Constants.APP_EVENT_BROADCAST_ACTION_DATA), "Y");
                    } else {
                        do_not_ask = false;
                        OtherAppEventDataBase.getInstance(NotifyPopUpActivity.this).insertDoAskStatus(getIntent().getStringExtra(Constants.APP_EVENT_BROADCAST_ACTION_DATA), "N");

                    }

                }
            });

            crossImage = (ImageView) dialog.findViewById(R.id.cross_button);
            crossImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (do_not_ask) {
                        // here need db storage to never ask
                    }
                    dialog.dismiss();
                    finish();
                    if (LokdonActivity.lokdonActivity != null)
                        LokdonActivity.lokdonActivity.finish();
                }
            });
            //+getAppName(getIntent().getStringExtra(Constants.APP_EVENT_BROADCAST_ACTION_DATA)))

            okButton = (Button) dialog.findViewById(R.id.ok_button);
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (do_not_ask)
                        OtherAppEventDataBase.getInstance(NotifyPopUpActivity.this).insertDefaultStatus(getIntent().getStringExtra(Constants.APP_EVENT_BROADCAST_ACTION_DATA), "Y");

                    Intent popUpActivity = new Intent(NotifyPopUpActivity.this, LokdonActivity.class);
                    popUpActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    popUpActivity.putExtra(Constants.INTENT_APP_TYPE, Constants.APP_TYPE_MES);
                    NotifyPopUpActivity.this.startActivity(popUpActivity);
                    dialog.dismiss();
                    NotifyPopUpActivity.this.finish();
                }
            });

            cancelButton = (Button) dialog.findViewById(R.id.cancel_button);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dialog.dismiss();
                    finish();
                    if (LokdonActivity.lokdonActivity != null)
                        LokdonActivity.lokdonActivity.finish();

                }
            });
        }

        dialog.show();

    }

    public String getAppName(String packageName) {


        try {
            packageManager = this.getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(packageName, 0);
        } catch (final PackageManager.NameNotFoundException e) {
            applicationInfo = null;
            DebugUtil.printExceptionLog("onStartCommand  :: ", e);

        }
        final String applicationName = (String) (applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo) : "(unknown)");

        return applicationName;
    }
}
