package com.offshoreindian.lokdon.fragmentUi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.encryption.SmsEncryption;
import com.offshoreindian.lokdon.framework.BaseFragment;
import com.offshoreindian.lokdon.utils.AppEvents;
import com.offshoreindian.lokdon.utils.Constants;
import com.offshoreindian.lokdon.utils.DebugUtil;


public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private RelativeLayout splashBackgroud = null;
    private boolean isBackKeyPressed = false;
    private LinearLayout messengerLayout, scloudLayout, driveLayout, awsLayout, azureLayout;

    public HomeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, null);


//        SmsEncryption.getInstance().getEncryptSms("hello world","jdsafkjksdf");

        applicationController.getActivity().showToolBar();
        applicationController.getActivity().hideHomeScreenLogo();
        applicationController.getActivity().showAndHideFloatIcon(View.GONE);

        messengerLayout = (LinearLayout) view.findViewById(R.id.messenger_layout);
        messengerLayout.setOnClickListener(this);

        scloudLayout = (LinearLayout) view.findViewById(R.id.scloud_layout);
        scloudLayout.setOnClickListener(this);

        driveLayout = (LinearLayout) view.findViewById(R.id.drive_layout);
        driveLayout.setOnClickListener(this);

        awsLayout = (LinearLayout) view.findViewById(R.id.aws_layout);
        awsLayout.setOnClickListener(this);

        azureLayout = (LinearLayout) view.findViewById(R.id.azure_layout);
        azureLayout.setOnClickListener(this);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        String app_type = getActivity().getIntent().getStringExtra("APP_TYPE");
        DebugUtil.printLog(" app_type app_type ::: "+app_type);
        if(app_type!=null)
        {
            if(app_type.equalsIgnoreCase(Constants.APP_TYPE_MES))
            {
                getActivity().getIntent().putExtra(Constants.INTENT_APP_TYPE,"");
                applicationController.handleEvent(AppEvents.EVENT_MESSENGER_SCREEN, null, true);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.messenger_layout: {

                applicationController.handleEvent(AppEvents.EVENT_MESSENGER_SCREEN, null, true);
            }
            break;
            case R.id.scloud_layout: {

                applicationController.handleEvent(AppEvents.EVENT_ADD_PROVIDER_FRAGMENT, null, true);
            }
            break;
            case R.id.aws_layout: {
                applicationController.handleEvent(AppEvents.EVENT_AWS_SCREEN, null, true);

            }
            break;
            case R.id.drive_layout: {
             //   applicationController.handleEvent(AppEvents.EVENT_GOOGLE_DRIVE_SCREEN, null, true);
                applicationController.handleEvent(AppEvents.EVENT_WEB_VIEW, "https://drive.google.com", true);

                //applicationController.showToastMessages("Google Drive Clicked");
            }
            break;
            case R.id.azure_layout: {
                 applicationController.handleEvent(AppEvents.EVENT_AZURE_SCREEN, null, true);
                //applicationController.showToastMessages("Azure Clicked");
            }
            break;


        }


    }


    @Override
    public void updateView() {
        super.updateView();


    }


    @Override
    public boolean onBackKeyPressed() {
        if (!isBackKeyPressed) {
            isBackKeyPressed = true;
            applicationController.showToastMessages("Press again back key for Exit.");
        } else {
            getActivity().finish();
        }
        return true;
    }


}
