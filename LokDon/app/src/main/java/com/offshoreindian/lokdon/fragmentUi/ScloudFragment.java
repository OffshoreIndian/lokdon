package com.offshoreindian.lokdon.fragmentUi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.framework.BaseFragment;
import com.offshoreindian.lokdon.utils.AppEvents;


public class ScloudFragment extends BaseFragment implements View.OnClickListener{

    private LinearLayout messengerLayout, scloudLayout, driveLayout, awsLayout, azureLayout;

    public ScloudFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scloud, null);
        //applicationController.getActivity().showHomeScreenLogo();
        applicationController.getActivity().showToolBar();
        messengerLayout = (LinearLayout) view.findViewById(R.id.messenger_layout);
        messengerLayout.setOnClickListener(this);

        scloudLayout = (LinearLayout) view.findViewById(R.id.scloud_layout);
        scloudLayout.setOnClickListener(this);

//        driveLayout = (LinearLayout) view.findViewById(R.id.drive_layout);
//        driveLayout.setOnClickListener(this);

        awsLayout = (LinearLayout) view.findViewById(R.id.aws_layout);
        awsLayout.setOnClickListener(this);

        azureLayout = (LinearLayout) view.findViewById(R.id.azure_layout);
        azureLayout.setOnClickListener(this);

        return view;
    }
    @Override
    public void onClick(View v) {
            switch (v.getId())
            {

                case R.id.messenger_layout: {
                    // applicationController.showToastMessages("Messenger Clicked");
                    applicationController.handleEvent(AppEvents.EVENT_DROP_BOX, "https://www.dropbox.com/", true);
                }
                break;
                case R.id.scloud_layout: {
                    //applicationController.showToastMessages(" sCloud clicked");
                    applicationController.handleEvent(AppEvents.EVENT_WEB_VIEW, "https://onedrive.live.com", true);
                }
                break;
                case R.id.aws_layout: {
                    applicationController.handleEvent(AppEvents.EVENT_WEB_VIEW, "https://www.mediafire.com", true);
                    //applicationController.showToastMessages(" AWS Clicked");
                }
                break;

                case R.id.azure_layout: {
                    applicationController.handleEvent(AppEvents.EVENT_WEB_VIEW, "https://www.sugarsync.com", true);
                    //applicationController.showToastMessages("Azure Clicked");
                }
                break;

            }


    }



    @Override
    public void updateView() {
        super.updateView();
    }




}
