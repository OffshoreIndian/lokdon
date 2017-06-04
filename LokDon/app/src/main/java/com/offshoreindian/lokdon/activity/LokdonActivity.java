package com.offshoreindian.lokdon.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.provider.Settings;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.adapter.DrawerProviderListAdapter;
import com.offshoreindian.lokdon.bean.DrawerProviderBean;
import com.offshoreindian.lokdon.encryption.KnightSolver;
import com.offshoreindian.lokdon.encryption.PassKeyController;
import com.offshoreindian.lokdon.framework.ApplicationController;
import com.offshoreindian.lokdon.services.AppTrackingService;
import com.offshoreindian.lokdon.services.UStats;
import com.offshoreindian.lokdon.utils.AppEvents;
import com.offshoreindian.lokdon.utils.Constants;
import com.offshoreindian.lokdon.utils.DebugUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LokdonActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar = null;
    private ApplicationController applicationController = null;
    private LinearLayout borderLineLayout = null;
    private DrawerLayout drawer = null;
    private LinearLayout homeScreenLogoLaayout = null;
    private ListView drawerListView;
    public static LokdonActivity lokdonActivity;
    private FloatingActionButton floatingActionButton = null;
    private DrawerProviderListAdapter drawerProviderListAdapter;
    private TextView drawer_email,drawer_username,drawer_contact;
    private LinearLayout favourite_layout,upload_layout,setting_layout,refer_layout,exit_layout,help_layout,term_layout;
    /**
     * Id to identify a contacts permission request.
     */
    private static final int REQUEST_CONTACTS = 1;


    private static String[] PERMISSIONS_CONTACT = {
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,

    };

    public static String generatedKey;
    public ArrayList<DrawerProviderBean> drawerProviderBeanArrayList;
    ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokdon);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
            // static instance to destroy the full application from other component -like NotifyPopUpActivity
        lokdonActivity = this;

        drawer_email = (TextView) findViewById(R.id.drawer_email);
        drawer_username = (TextView) findViewById(R.id.drawer_username);
        drawer_contact = (TextView) findViewById(R.id.drawer_contact);


        drawerProviderBeanArrayList = new ArrayList<DrawerProviderBean>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {

            //Check if permission enabled
            if (UStats.getUsageStatsList(this).isEmpty()) {
                Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                startActivity(intent);
            }
            //UStats.printUsageStats(UStats.getUsageStatsList(this));
            // UStats.getStats(this);


        }





        askForPermission();
        homeScreenLogoLaayout = (LinearLayout) findViewById(R.id.home_screen_logo);
        homeScreenLogoLaayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applicationController.handleEvent(AppEvents.EVENT_HOME_SCREEN, null, true);
            }
        });









        borderLineLayout = (LinearLayout) findViewById(R.id.black_boder_line);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(false);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {
                            menuItem.setChecked(true);
                            drawer.closeDrawers();
                            return true;
                        }
                    });
        }


          toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.nav_open, R.string.nav_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();




        applicationController = ApplicationController.getInstance();
        applicationController.setActivity(this);
        applicationController.init();
        if(applicationController.getValueFromPerference(Constants.WELCOME_KEY) ==null){
            applicationController.handleEvent(AppEvents.EVENT_WELCOME_SCREEN, null, false);

        }
        else {
            applicationController.handleEvent(AppEvents.EVENT_SPLASH_SCREEN, null, false);
        }

        startService(new Intent(this, AppTrackingService.class));


        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setVisibility(View.GONE);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if fragment is not handle the event then open provider screen
                int currentFragmentIndex = applicationController.getAddedFragmentList().size() - 1;
                if(!applicationController.getAddedFragmentList().get(currentFragmentIndex).handleFloatingButtonClick())
                {
                    applicationController.handleEvent(AppEvents.EVENT_ADD_PROVIDER_FRAGMENT);

                }

            }
        });


        // init drawer list
        drawerListView = (ListView) findViewById(R.id.navigation_drawer_list);
        updateProviderListView();
        handleProviderListEvent();

        favourite_layout = (LinearLayout) findViewById(R.id.favourite_layout);
        favourite_layout.setOnClickListener(this);

        upload_layout = (LinearLayout) findViewById(R.id.upload_layout);
        upload_layout.setOnClickListener(this);

        setting_layout = (LinearLayout) findViewById(R.id.setting_layout);
        setting_layout.setOnClickListener(this);

        refer_layout = (LinearLayout) findViewById(R.id.refer_layout);
        refer_layout.setOnClickListener(this);

        exit_layout = (LinearLayout) findViewById(R.id.exit_layout);
        exit_layout.setOnClickListener(this);

        help_layout = (LinearLayout) findViewById(R.id.help_layout);
        help_layout.setOnClickListener(this);

        term_layout = (LinearLayout) findViewById(R.id.term_layout);
        term_layout.setOnClickListener(this);



    }


    public void updateProviderListView()
    {
        drawerProviderBeanArrayList.clear();
        for (int i = 0;i<Constants.provideTagId.length;i++)
        {
            if(applicationController.getValueFromPerference(Constants.provideTagId[i]+"") != null) {
                drawerProviderBeanArrayList.add(new DrawerProviderBean(R.mipmap.google_drive, Constants.provideName[i], Constants.provideTagId[i]));
            }

        }
        drawerProviderBeanArrayList.add(new DrawerProviderBean(R.mipmap.add,"Add Provider",-1));

        if(drawerProviderListAdapter!= null)
        {
          //  drawerProviderListAdapter.getDrawerProviderBeanArrayList().clear();
            drawerProviderListAdapter.setDrawerProviderBeanArrayList(drawerProviderBeanArrayList);
            drawerProviderListAdapter.notifyDataSetChanged();
        }
        else
        {
            drawerProviderListAdapter =  new DrawerProviderListAdapter(this,drawerProviderBeanArrayList);
            drawerListView.setAdapter(drawerProviderListAdapter);
        }

    }
    public void handleProviderListEvent()
    {
        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawer.closeDrawer(GravityCompat.START);
                applicationController.handleAppLaunchEvent(drawerProviderBeanArrayList.get(position).getTagId());

//                switch (drawerProviderBeanArrayList.get(position).getTagId())
//                {
//                    case Constants.PROVIDER_DROPBOX:
//                    {
//                        //drawer.closeDrawer(GravityCompat.START);
//                        // applicationController.showToastMessages("PROVIDER_DROPBOX");
//                        applicationController.handleEvent(AppEvents.EVENT_DROP_BOX,null,true);
//
//                    }
//                    break;
//                    case Constants.PROVIDER_MESSENGER:
//                    {
//                        applicationController.handleEvent(AppEvents.EVENT_MESSENGER_SCREEN,null,true);
//                    }
//                    break;
//                    case Constants.PROVIDER_GOOGLE_DRIVE:
//                    {
//                        applicationController.handleEvent(AppEvents.EVENT_GOOGLE_DRIVE_SCREEN,null,true);
//                    }
//                    break;
//                    case Constants.PROVIDER_ONE_DRIVE:
//                    {
//                        applicationController.showToastMessages("PROVIDER_ONE_DRIVE");
//                    }
//                    break;
//                    case Constants.PROVIDER_ONE_DRIVE_FOR_BUSINESS:
//                    {
//                        applicationController.showToastMessages("PROVIDER_ONE_DRIVE_FOR_BUSINESS");
//                    }
//                    break;
//                    case Constants.PROVIDER_BOX:
//                    {
//                        applicationController.showToastMessages("PROVIDER_BOX");
//                    }
//                    break;
//                    case Constants.PROVIDER_SUGARSYNC:
//                    {
//                        applicationController.showToastMessages("PROVIDER_SUGARSYNC");
//                    }
//                    break;
//                    case Constants.PROVIDER_AMAZON_CLOUD_DRIVE:
//                    {
//                        applicationController.showToastMessages("PROVIDER_AMAZON_CLOUD_DRIVE");
//                    }
//                    break;
//                    case Constants.PROVIDER_AMAZON_S3:
//                    {
//                        applicationController.showToastMessages("PROVIDER_AMAZON_S3");
//                    }
//                    break;
//                    case Constants.PROVIDER_TELEKOM_MAGENTACLOUD:
//                    {
//                        applicationController.showToastMessages("PROVIDER_TELEKOM_MAGENTACLOUD");
//                    }
//                    break;
//                    case Constants.PROVIDER_STRATO_HIDRIVE:
//                    {
//                        applicationController.showToastMessages("PROVIDER_STRATO_HIDRIVE");
//                    }
//                    break;
//                    case Constants.PROVIDER_GMX_MEDIACENTER:
//                    {
//                        applicationController.showToastMessages("PROVIDER_GMX_MEDIACENTER");
//                    }
//                    break;
//                    case Constants.PROVIDER_WEB_DE_SMARTDRIVE:
//                    {
//                        applicationController.showToastMessages("PROVIDER_WEB_DE_SMARTDRIVE");
//                    }
//                    break;
//                    case Constants.PROVIDER_ORANGE_CLOUD:
//                    {
//                        applicationController.showToastMessages("PROVIDER_ORANGE_CLOUD");
//                    }
//                    break;
//                    case Constants.PROVIDER_HUBIC:
//                    {
//                        applicationController.showToastMessages("PROVIDER_HUBIC");
//                    }
//                    break;
//                    case Constants.PROVIDER_MAILBOX_ORG_DRIVE:
//                    {
//                        applicationController.showToastMessages("PROVIDER_MAILBOX_ORG_DRIVE");
//                    }
//                    break;
//
//                    case Constants.PROVIDER_CLOUDME:
//                    {
//                        applicationController.showToastMessages("PROVIDER_CLOUDME");
//                    }
//                    break;
//                    case Constants.PROVIDER_GRAU_DATASPACE:
//                    {
//                        applicationController.showToastMessages("PROVIDER_GRAU_DATASPACE");
//                    }
//                    break;
//                    case Constants.PROVIDER_STOREGATE:
//                    {
//                        applicationController.showToastMessages("PROVIDER_STOREGATE");
//                    }
//                    break;
//                    case Constants.PROVIDER_EGNYTE:
//                    {
//                        applicationController.showToastMessages("PROVIDER_EGNYTE");
//                    }
//                    break;
//                    case Constants.PROVIDER_CUBBY:
//                    {
//                        applicationController.showToastMessages("PROVIDER_CUBBY");
//                    }
//                    break;
//                    case Constants.PROVIDER_PSMAIL_CABINET:
//                    {
//                        applicationController.showToastMessages("PROVIDER_PSMAIL_CABINET");
//                    }
//                    break;
//                    case Constants.PROVIDER_LIVEDRIVE:
//                    {
//                        applicationController.showToastMessages("PROVIDER_LIVEDRIVE");
//                    }
//                    break;
//                    case Constants.PROVIDER_YANDEX_DISK:
//                    {
//                        applicationController.showToastMessages("PROVIDER_YANDEX_DISK");
//                    }
//                    break;
//                    case Constants.PROVIDER_WEBDAV_ADVANCED:
//                    {
//                        applicationController.showToastMessages("PROVIDER_WEBDAV_ADVANCED");
//                    }
//                    break;
//                    case Constants.PROVIDER_LOCAL_STORAGE:
//                    {
//                        applicationController.showToastMessages("PROVIDER_LOCAL_STORAGE");
//                    }
//                    break;
//                }

            }
        });
    }

    /**
     * show and hide the floatingActionIcon
     * @param value
     */
    public void showAndHideFloatIcon(int value)
    {
        floatingActionButton.setVisibility(value);
    }
    protected void sendSMSMessage(String text) {
        Log.i("Send SMS", "");
        String phoneNo = "+918826166350";

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, text, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    /**
     * Called when the 'show camera' button is clicked.
     * Callback is defined in resource layout definition.
     */
    public void askForPermission() {
        Log.i(TAG, "Show contacts button pressed. Checking permissions.");

        // Verify that all required contact permissions have been granted.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
            requestForPermissions();
        }
    }

    String TAG = "TAG";

    /**
     * Requests the Contacts permissions.
     * If the permission has been denied previously, a SnackBar will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    private void requestForPermissions() {
        // BEGIN_INCLUDE(contacts_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                ) {

            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example, if the request has been denied previously.
//            Log.i(TAG, "Displaying contacts permission rationale to provide additional context.");
            // startAstDstSystem(data);
            // Display a SnackBar with an explanation and a button to trigger the request.

        } else {
            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions((Activity) this, PERMISSIONS_CONTACT, REQUEST_CONTACTS);
        }
    }


    public TextView getDrawer_email() {
        return drawer_email;
    }

    public void setDrawer_email(TextView drawer_email) {
        this.drawer_email = drawer_email;
    }

    public TextView getDrawer_username() {
        return drawer_username;
    }

    public void setDrawer_username(TextView drawer_username) {
        this.drawer_username = drawer_username;
    }

    public TextView getDrawer_contact() {
        return drawer_contact;
    }

    public void setDrawer_contact(TextView drawer_contact) {
        this.drawer_contact = drawer_contact;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {


        if (requestCode == REQUEST_CONTACTS) {
            // for each permission check if the user grantet/denied them
            // you may want to group the rationale in a single dialog,
            // this is just an example
            int count = 0;
            for (int i = 0, len = permissions.length; i < len; i++) {
                String permission = permissions[i];
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    count++;
                }
            }

            if (count == permissions.length) {
                //Constant.printLog("pravesh all permission recieved ");
                //  Toast.makeText(context, "All permission Recieved  ", Toast.LENGTH_SHORT).show();
            } else {
                //  Constant.printLog("some permission denied");
                //Toast.makeText(context, "Permission Dined ", Toast.LENGTH_SHORT).show();

            }
        }

    }

    ApplicationInfo applicationInfo;

    public String getAppName(PackageManager pm, String packageName) {


        try {
            applicationInfo = pm.getApplicationInfo(packageName, 0);
        } catch (final PackageManager.NameNotFoundException e) {
            applicationInfo = null;

        }
        final String applicationName = (String) (applicationInfo != null ? pm.getApplicationLabel(applicationInfo) : "(unknown)");
        return applicationName;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_lokdon, menu);
//        return true;
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.add_icon) {
//            applicationController.showToastMessages(" Add Button Click   ");
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (!applicationController.applicationControllerBackKeyPressed())
                super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DebugUtil.printLog(" Activity OnDestroy Called");
        if (applicationController != null) {
            applicationController.destroyApplicationController();
            applicationController = null;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    public void showHomeScreenLogo() {
        if (homeScreenLogoLaayout != null)
            homeScreenLogoLaayout.setVisibility(View.VISIBLE);
    }


    public void hideHomeScreenLogo() {
        if (homeScreenLogoLaayout != null)
            homeScreenLogoLaayout.setVisibility(View.INVISIBLE);
    }


    public void showToolBar() {
//        if(drawer!=null) {
//            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
//            drawer.closeDrawer(GravityCompat.START);
//        }

        toolbar.setVisibility(View.VISIBLE);
      //  borderLineLayout.setVisibility(View.VISIBLE);
    }

    public void hideToolBar() {
//        if(drawer!=null)
        //drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        borderLineLayout.setVisibility(View.GONE);
       // toolbar.setVisibility(View.GONE);
    }


    public void setToolBarTitle(String title)
    {
        toolbar.setTitle(title);
    }

    public void hideToggleIcon(boolean value)
    {
        toggle.setDrawerIndicatorEnabled(value);
    }


    @Override
    public void onClick(View v) {

        if(drawer!=null)
            drawer.closeDrawer(GravityCompat.START);
        switch (v.getId())
        {

            case R.id.favourite_layout:
            {
                applicationController.handleEvent(AppEvents.EVENT_FAVOURITES,null,true);
            }break;
            case R.id.upload_layout:
            {
                applicationController.handleEvent(AppEvents.EVENT_UPLOAD_MENU,null,true);


            }break;
            case R.id.setting_layout:
            {
                 applicationController.handleEvent(AppEvents.EVENT_SETTING_SCREEN,null,true);
            }break;
            case R.id.refer_layout:
            {
                applicationController.showToastMessages("refer Click");

            }break;
            case R.id.exit_layout:
            {
                applicationController.putValueInPerference(Constants.KEY_USER_NAME,null);
               this.finish();

            }break;
            case R.id.help_layout:
            {
                applicationController.handleEvent(AppEvents.EVENT_HELP_SCREEN,null,true);

            }break;
            case R.id.term_layout:
            {
                applicationController.handleEvent(AppEvents.EVENT_TERMS_SCREEN,null,true);



            }break;
        }

    }
}
