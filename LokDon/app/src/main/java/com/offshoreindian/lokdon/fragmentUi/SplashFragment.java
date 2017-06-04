package com.offshoreindian.lokdon.fragmentUi;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.framework.BaseFragment;
import com.offshoreindian.lokdon.utils.AppEvents;
import com.offshoreindian.lokdon.utils.Constants;


public class SplashFragment extends BaseFragment  {
    private RelativeLayout splashBackgroud = null;

    public SplashFragment()
    {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_splash,null);

         applicationController.getActivity().hideToolBar();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(applicationController.getValueFromPerference(Constants.KEY_USER_NAME) != null)
                {
                   applicationController.handleEvent(AppEvents.EVENT_HOME_SCREEN);
                     applicationController.getActivity().getDrawer_contact().setText(applicationController.getValueFromPerference(Constants.KEY_PHONE_NO));
                    applicationController.getActivity().getDrawer_username().setText(applicationController.getValueFromPerference(Constants.KEY_USER_NAME));
                    applicationController.getActivity().getDrawer_email().setText(applicationController.getValueFromPerference(Constants.KEY_EMAIL_ID));

                    applicationController.setUserName(applicationController.getValueFromPerference(Constants.KEY_USER_NAME));
//                    int view =  applicationController.handleAppLaunchEvent(applicationController.getActivity().drawerProviderBeanArrayList.get(0).getTagId());
//                    applicationController.setHomeScreenViewID(view);
                }
                else
                {
                    applicationController.handleEvent(AppEvents.EVENT_LOGIN_SCREEN);

                }
            }
        }, 3000);
        return view ;
    }


    @Override
    public void updateView() {
        super.updateView();


    }






}
