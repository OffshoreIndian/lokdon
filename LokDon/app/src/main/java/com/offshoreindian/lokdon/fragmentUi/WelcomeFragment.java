package com.offshoreindian.lokdon.fragmentUi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.adapter.WelcomePagerAdapter;
import com.offshoreindian.lokdon.framework.BaseFragment;
import com.offshoreindian.lokdon.utils.AppEvents;
import com.offshoreindian.lokdon.utils.Constants;
import com.offshoreindian.lokdon.utils.DebugUtil;


public class WelcomeFragment extends BaseFragment implements View.OnClickListener {

    WelcomePagerAdapter welcomePagerAdapter;
    ViewPager viewPager;
    private LinearLayout indicator_1,indicator_2,indicator_3,indicator_4;//,indicator_5;
    public WelcomeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_welcome, null);
        applicationController.getActivity().showToolBar();
        applicationController.getActivity().hideHomeScreenLogo();
        applicationController.getActivity().showAndHideFloatIcon(View.GONE);
        applicationController.getActivity().setToolBarTitle("Welcome to Lonkon");
        applicationController.getActivity().hideToggleIcon(false);

        viewPager =(ViewPager) view.findViewById(R.id.welcome_pager);
        welcomePagerAdapter = new WelcomePagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(welcomePagerAdapter);

        indicator_1 = (LinearLayout) view.findViewById(R.id.indicator_1);
        indicator_2 = (LinearLayout) view.findViewById(R.id.indicator_2);
        indicator_3 = (LinearLayout) view.findViewById(R.id.indicator_3);
        indicator_4 = (LinearLayout) view.findViewById(R.id.indicator_4);
     //   indicator_5 = (LinearLayout) view.findViewById(R.id.indicator_5);
        indicator_1.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
        indicator_2.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
        indicator_3.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
        indicator_4.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
      //  indicator_5.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
        viewPager.setCurrentItem(0);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position)
                {
                    case 0: {
                        indicator_1.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
                        indicator_2.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                        indicator_3.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                        indicator_4.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                       // indicator_5.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                    }
                    break;
                    case 1: {
                        indicator_2.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
                        indicator_1.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                        indicator_3.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                        indicator_4.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                     //   indicator_5.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                    }
                    break;
                    case 2: {
                        indicator_3.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
                        indicator_1.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                        indicator_2.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                        indicator_4.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                       // indicator_5.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                    }
                    break;
                    case 3: {
                        indicator_4.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
                        indicator_2.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                        indicator_1.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                        indicator_3.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                        //indicator_5.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                    }
                    break;
                    case 4: {
                       // indicator_5.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
                        indicator_2.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                        indicator_3.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                        indicator_4.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                        indicator_1.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                    }
                    break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onClick(View v) {


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        applicationController.getActivity().setToolBarTitle("Lonkon");
        applicationController.getActivity().hideToggleIcon(true);

    }

    @Override
    public void updateView() {
        super.updateView();


    }


}
