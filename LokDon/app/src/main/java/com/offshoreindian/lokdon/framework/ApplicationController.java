package com.offshoreindian.lokdon.framework;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.activity.LokdonActivity;
import com.offshoreindian.lokdon.bean.DrawerProviderBean;
import com.offshoreindian.lokdon.utils.AppEvents;
import com.offshoreindian.lokdon.utils.Constants;
import com.offshoreindian.lokdon.utils.DebugUtil;

import java.util.ArrayList;
import java.util.Objects;


public class ApplicationController {
    SharedPreferences sharedpreferences = null;


    private static ApplicationController instance = null;
    private LokdonActivity activity;
    private ArrayList<BaseFragment> addedFragmentList = null;
    public ArrayList<BaseFragment> allAddedFragmentList = null;
    private FragmentTransaction fragmentTransaction;
    private int homeScreenViewID = -1;
    private String accessToken = "";
    private String refreshToken = "";
    private String userName = "";
    private FragmentFactory fragmentFactory;
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    private ApplicationController() {
        addedFragmentList = new ArrayList<BaseFragment>();
        allAddedFragmentList = new ArrayList<BaseFragment>();
        fragmentFactory = FragmentFactory.getInstance();
    }

    ;

    public static ApplicationController getInstance() {

        if (instance == null)
            instance = new ApplicationController();

        return instance;
    }

    public LokdonActivity getActivity() {
        return activity;
    }

    public void setActivity(LokdonActivity activity) {
        this.activity = activity;
    }


    public boolean isNetworkConnected() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void handleEvent(int eventID) {
        handleEvent(eventID, null, true);
    }

    public void handleEvent(int eventID, Object data) {
        handleEvent(eventID, data, true);

    }

    public void handleEvent(int eventId, Object data, boolean isAddToStack) {
        switch (eventId) {
            case AppEvents.EVENT_SPLASH_SCREEN: {
                addFragmentToBackStack(FragmentFactory.SPALSH_FRAGMENT, data, isAddToStack);
            }
            break;
            case AppEvents.EVENT_REGISTRATION_SCREEN: {
                addFragmentToBackStack(FragmentFactory.REGISTRATION_FRAGMENT, data, isAddToStack);
            }
            break;
            case AppEvents.EVENT_LOGIN_SCREEN: {
                addFragmentToBackStack(FragmentFactory.LOGIN_FRAGMENT, data, isAddToStack);
            }
            break;
            case AppEvents.EVENT_HOME_SCREEN: {
                addFragmentToBackStack(FragmentFactory.HOME_FRAGMENT, data, isAddToStack);
                //addFragmentToBackStack(FragmentFactory.PROVIDER_SCREEN, data, isAddToStack);
            }
            break;
            case AppEvents.EVENT_MESSENGER_SCREEN: {
                addFragmentToBackStack(FragmentFactory.MESSENGER_FRAGMENT, data, isAddToStack);
            }
            break;
            case AppEvents.EVENT_SCLOUD_SCREEN: {
                addFragmentToBackStack(FragmentFactory.SCLOUD_FREGMENT, data, isAddToStack);
            }
            break;
            case AppEvents.EVENT_GOOGLE_DRIVE_SCREEN: {
                addFragmentToBackStack(FragmentFactory.GOOGLE_DRIVE_FRAGMENT, data, isAddToStack);
            }
            break;
            case AppEvents.EVENT_AWS_SCREEN: {
                addFragmentToBackStack(FragmentFactory.AWS_FRAGMENT, data, isAddToStack);
            }
            break;
            case AppEvents.EVENT_AZURE_SCREEN: {
                addFragmentToBackStack(FragmentFactory.AZURE_FRAGMENT, data, isAddToStack);
            }
            break;
            case AppEvents.EVENT_SHOW_MESSAGE_SCREEN: {
                addFragmentToBackStack(FragmentFactory.SHOE_MESSAGE_FRAGMENT, data, isAddToStack);
            }
            break;
            case AppEvents.EVENT_WEB_VIEW: {
                addFragmentToBackStack(FragmentFactory.WEB_VIEW_FRAGMENT, data, isAddToStack);
            }
            break;
            case AppEvents.EVENT_DROP_BOX: {
                addFragmentToBackStack(FragmentFactory.DROP_BOX_FRAGMENT, data, isAddToStack);
            }
            break;
            case AppEvents.EVENT_PROVIDER_FRAGMENT: {
                addFragmentToBackStack(FragmentFactory.PROVIDER_SCREEN, data, isAddToStack);
//                addFragmentToBackStack(FragmentFactory.HOME_FRAGMENT, data, isAddToStack);
            }
            break;
            case AppEvents.EVENT_ADD_PROVIDER_FRAGMENT: {
                addFragmentToBackStack(FragmentFactory.ADD_PROVIDER_SCREEN, data, isAddToStack);
            }
            break;
            case AppEvents.EVENT_WELCOME_SCREEN: {
                addFragmentToBackStack(FragmentFactory.WELCOME_SCREEN, data, isAddToStack);
            }
            break;
            case AppEvents.EVENT_SETTING_SCREEN: {
                addFragmentToBackStack(FragmentFactory.SETTING_FRAGMENT, data, isAddToStack);
            }
            break;
            case AppEvents.EVENT_TERMS_SCREEN: {
                addFragmentToBackStack(FragmentFactory.TERMS_FRAGMENT, data, isAddToStack);
            }
            break;
            case AppEvents.EVENT_HELP_SCREEN: {
                addFragmentToBackStack(FragmentFactory.HELP_FRAGMENT, data, isAddToStack);
            }
            break;
            case AppEvents.EVENT_UPLOAD_MENU: {
                addFragmentToBackStack(FragmentFactory.UPLOAD_FRAGMENT, data, isAddToStack);
            }
            break;
        }
    }


    /**
     * when application launch at that time we check which provide is added first
     * @param tagId
     */
    public int handleAppLaunchEvent(int tagId)
    {

             int homeViewId = -1;
           // DrawerProviderBean bean = getActivity().drawerProviderBeanArrayList.get(getActivity().drawerProviderBeanArrayList.size()-1)
            switch (tagId)
            {
                case Constants.NO_PROVIDER_ADD:
                {
                    //drawer.closeDrawer(GravityCompat.START);
                    // applicationController.showToastMessages("PROVIDER_DROPBOX");
                    handleEvent(AppEvents.EVENT_PROVIDER_FRAGMENT,null,true);
                    homeViewId = FragmentFactory.ADD_PROVIDER_SCREEN;
                }
                break;
                case Constants.PROVIDER_DROPBOX:
                {
                    //drawer.closeDrawer(GravityCompat.START);
                    // applicationController.showToastMessages("PROVIDER_DROPBOX");
                     handleEvent(AppEvents.EVENT_DROP_BOX,"",true);
                    homeViewId = FragmentFactory.DROP_BOX_FRAGMENT;
                }
                break;
//                case Constants.PROVIDER_MESSENGER:
//                {
//                    handleEvent(AppEvents.EVENT_MESSENGER_SCREEN,null,true);
//                    homeViewId = FragmentFactory.MESSENGER_FRAGMENT;
//                }
//                break;
                case Constants.PROVIDER_GOOGLE_DRIVE:
                {
                     handleEvent(AppEvents.EVENT_GOOGLE_DRIVE_SCREEN,null,true);
                    homeViewId = FragmentFactory.GOOGLE_DRIVE_FRAGMENT;
                }
                break;
                case Constants.PROVIDER_ONE_DRIVE:
                {
                     showToastMessages("PROVIDER_ONE_DRIVE");
                }
                break;
                case Constants.PROVIDER_ONE_DRIVE_FOR_BUSINESS:
                {
                    showToastMessages("PROVIDER_ONE_DRIVE_FOR_BUSINESS");
                }
                break;
                case Constants.PROVIDER_BOX:
                {
                   showToastMessages("PROVIDER_BOX");
                }
                break;
                case Constants.PROVIDER_SUGARSYNC:
                {
                    showToastMessages("PROVIDER_SUGARSYNC");
                }
                break;
//                case Constants.PROVIDER_AMAZON_CLOUD_DRIVE:
//                {
//                    showToastMessages("PROVIDER_AMAZON_CLOUD_DRIVE");
//                }
//                break;
//                case Constants.PROVIDER_AMAZON_S3:
//                {
//                   showToastMessages("PROVIDER_AMAZON_S3");
//                }
//                break;
                case Constants.PROVIDER_TELEKOM_MAGENTACLOUD:
                {
                   showToastMessages("PROVIDER_TELEKOM_MAGENTACLOUD");
                }
                break;
                case Constants.PROVIDER_STRATO_HIDRIVE:
                {
                    showToastMessages("PROVIDER_STRATO_HIDRIVE");
                }
                break;
                case Constants.PROVIDER_GMX_MEDIACENTER:
                {
                    showToastMessages("PROVIDER_GMX_MEDIACENTER");
                }
                break;
                case Constants.PROVIDER_WEB_DE_SMARTDRIVE:
                {
                     showToastMessages("PROVIDER_WEB_DE_SMARTDRIVE");
                }
                break;
                case Constants.PROVIDER_ORANGE_CLOUD:
                {
                     showToastMessages("PROVIDER_ORANGE_CLOUD");
                }
                break;
                case Constants.PROVIDER_HUBIC:
                {
                    showToastMessages("PROVIDER_HUBIC");
                }
                break;
                case Constants.PROVIDER_MAILBOX_ORG_DRIVE:
                {
                     showToastMessages("PROVIDER_MAILBOX_ORG_DRIVE");
                }
                break;

                case Constants.PROVIDER_CLOUDME:
                {
                     showToastMessages("PROVIDER_CLOUDME");
                }
                break;
                case Constants.PROVIDER_GRAU_DATASPACE:
                {
                     showToastMessages("PROVIDER_GRAU_DATASPACE");
                }
                break;
                case Constants.PROVIDER_STOREGATE:
                {
                     showToastMessages("PROVIDER_STOREGATE");
                }
                break;
                case Constants.PROVIDER_EGNYTE:
                {
                     showToastMessages("PROVIDER_EGNYTE");
                }
                break;
                case Constants.PROVIDER_CUBBY:
                {
                     showToastMessages("PROVIDER_CUBBY");
                }
                break;
                case Constants.PROVIDER_PSMAIL_CABINET:
                {
                    showToastMessages("PROVIDER_PSMAIL_CABINET");
                }
                break;
                case Constants.PROVIDER_LIVEDRIVE:
                {
                     showToastMessages("PROVIDER_LIVEDRIVE");
                }
                break;
                case Constants.PROVIDER_YANDEX_DISK:
                {
                     showToastMessages("PROVIDER_YANDEX_DISK");
                }
                break;
                case Constants.PROVIDER_WEBDAV_ADVANCED:
                {
                     showToastMessages("PROVIDER_WEBDAV_ADVANCED");
                }
                break;
                case Constants.PROVIDER_LOCAL_STORAGE:
                {
                     showToastMessages("PROVIDER_LOCAL_STORAGE");
                }
                break;

            }

        return homeViewId;
    }
    public void showErrorDialogBox(String title, String message, int type) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                new ContextThemeWrapper(getActivity(), android.R.style.Theme_DeviceDefault_Light));

        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

//                        if(fragmentFactory!=null) {
//                            fragmentFactory.destroyAllView();
//                            fragmentFactory = null;
//                        }
//                        instance = null;
                        getActivity().finish();



                    }
                })
                .setNegativeButton("Report Us", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Intent emailIntent = new Intent(Intent.ACTION_SEND);

                            emailIntent.setData(Uri.parse("mailto:"));
                            emailIntent.setType("text/plain");
                            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"guptajhansi1586@gmail.com"});
                            // change the subject message and text
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Crash Report");
                            emailIntent.putExtra(Intent.EXTRA_TEXT, "crash message .. sent by device");


                            getActivity().startActivity(Intent.createChooser(emailIntent, "Send mail..."));

                        } catch (android.content.ActivityNotFoundException ex) {
                        }

//                        if(fragmentFactory!=null) {
//                            fragmentFactory.destroyAllView();
//                            fragmentFactory = null;
//                        }
//                        instance = null;
                        getActivity().finish();


                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }




    public void addFragmentToBackStack(int viewId, Object data, boolean isAddToStack) {
        addFragmentToStack(viewId, data, isAddToStack);
    }




    private void addFragmentToStack(int viewID, Object data, boolean isScreenAddToStack) {


        if(fragmentFactory == null)
            return;


        BaseFragment fragment = fragmentFactory.getFragmentView(viewID);
        if(fragment == null)
            return;

        fragment.setFragmentViewId(viewID);// set the view id of fragment
        fragment.setFragmentData(data);// set the data
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();


        if (fragment != null) {
            fragmentTransaction = fragmentManager.beginTransaction();
            int currentFragmentIndex = addedFragmentList.size() - 1;

            for (int index = 0; index < addedFragmentList.size(); index++) {
                if (addedFragmentList.get(index).equals(fragment)) {
                    if (index == currentFragmentIndex) {
                         return;
                    } else {
                        for (int temp = currentFragmentIndex; temp > index; temp--) {
                            fragmentManager.popBackStack();
                            fragmentFactory.destroyView(addedFragmentList.get(temp).getFragmentViewId());
                            addedFragmentList.remove(temp);

                        }
                        return;
                    }
                }
            }

            fragmentTransaction.replace(R.id.frame_layout, fragment, "lokdon");

            if (isScreenAddToStack) {
                if (addedFragmentList.size() > 0) {
                    fragmentTransaction.addToBackStack(fragment.getClass().getName());
                }
                addedFragmentList.add(fragment);
            }
            allAddedFragmentList.add(fragment);

            fragmentTransaction.commitAllowingStateLoss();
            System.gc();
        }
    }



    public void addFragment( int viewID, Object data, boolean isScreenAddToStack) {


        if(fragmentFactory == null)
            return;


        BaseFragment fragment = fragmentFactory.getFragmentView(viewID);
        if(fragment == null)
            return;

        fragment.setFragmentViewId(viewID);// set the view id of fragment
        fragment.setFragmentData(data);// set the data
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();


        if (fragment != null) {
            fragmentTransaction = fragmentManager.beginTransaction();
//            int currentFragmentIndex = addedFragmentList.size() - 1;
//
//            for (int index = 0; index < addedFragmentList.size(); index++) {
//                if (addedFragmentList.get(index).equals(fragment)) {
//                    if (index == currentFragmentIndex) {
//                        return;
//                    } else {
//                        for (int temp = currentFragmentIndex; temp > index; temp--) {
//                            fragmentManager.popBackStack();
//                            fragmentFactory.destroyView(addedFragmentList.get(temp).getFragmentViewId());
//                            addedFragmentList.remove(temp);
//
//                        }
//                        return;
//                    }
//                }
//            }

            fragmentTransaction.replace(R.id.frame_layout, fragment, "lokdon");

            if (isScreenAddToStack) {
                if (addedFragmentList.size() > 0) {
                    fragmentTransaction.addToBackStack(fragment.getClass().getName());
                }
                addedFragmentList.add(fragment);
            }
            allAddedFragmentList.add(fragment);

            fragmentTransaction.commitAllowingStateLoss();
            System.gc();
        }
    }


    public ArrayList<BaseFragment> getAddedFragmentList() {
        return addedFragmentList;
    }

    public void setAddedFragmentList(ArrayList<BaseFragment> addedFragmentList) {
        this.addedFragmentList = addedFragmentList;
    }

    boolean isBackPressed = false;
    public boolean applicationControllerBackKeyPressed() {

        if (addedFragmentList.size() > 0) {

            int currentIndex = addedFragmentList.size() - 1;
            if (addedFragmentList.get(currentIndex).onBackKeyPressed()) {
                isBackPressed = false;
                return true;
            }

            if (currentIndex != 0) {
                int viewId = addedFragmentList.get(currentIndex).getFragmentViewId();


                    isBackPressed = false;
                    addedFragmentList.remove(currentIndex);
                    fragmentFactory.destroyView(viewId);
                    // addedFragmentList.get(currentIndex-1).enableFragment();
                    fragmentManager.popBackStack();
                    System.gc();


                return true;
            }

        }

        return false;
    }

    /**
     * this will remove all added fragment and add new provide fragment id
     *
     * @param viewID
     * @param data
     */
    private void removeOthersAddThisFragment(int viewID, Object data) {
        BaseFragment fragment = fragmentFactory.getFragmentView(viewID);
        // fragment.setFragmentViewID(viewID);// set the view id of fragment

        if (fragment != null) {
            fragmentTransaction = fragmentManager.beginTransaction();
            int currentFragmentIndex = addedFragmentList.size() - 1;
            for (int temp = currentFragmentIndex; temp >= 0; temp--) {
                fragmentManager.popBackStack();
                addedFragmentList.remove(temp);
                fragmentFactory.destroyView(viewID);
            }
            fragmentTransaction.replace(R.id.frame_layout, fragment, "hello");
            //  fragment.enableFragment();
            if (addedFragmentList.size() > 0) {
                fragmentTransaction.addToBackStack(fragment.getClass().getName());
            }
            addedFragmentList.add(fragment);
            fragmentTransaction.commitAllowingStateLoss();
            System.gc();
        }
    }


    FragmentManager fragmentManager = null;

    public void init() {
        sharedpreferences = getActivity().getSharedPreferences(Constants.LOKDON_PERFERENCE, Context.MODE_PRIVATE);
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

    }

    public void putValueInPerference(String key, String value) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getValueFromPerference(String key) {
        return sharedpreferences.getString(key, null);
    }


    public void showToastMessages(final String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                LayoutInflater inflater = getActivity().getLayoutInflater();
                View toastView = inflater.inflate(R.layout.custom_toast, null);
                TextView textView = (TextView) toastView.findViewById(R.id.custom_toast_textView);
                textView.setText(message);
                Toast toast = new Toast(getActivity());
                toast.setView(toastView);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();

            }
        });

    }


    public void destroyApplicationController()
    {
        if(fragmentFactory!=null) {
            fragmentFactory.destroyAllView();
            fragmentFactory = null;
        }

        if(addedFragmentList != null)
        {
            addedFragmentList.clear();
            addedFragmentList = null;
        }
        if(allAddedFragmentList != null)
        {
            allAddedFragmentList.clear();
            allAddedFragmentList = null;
        }
        activity = null;
        instance = null;
    }


    public int getHomeScreenViewID() {
        return homeScreenViewID;
    }

    public void setHomeScreenViewID(int homeScreenViewID) {
        this.homeScreenViewID = homeScreenViewID;
    }
}
