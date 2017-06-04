package com.offshoreindian.lokdon.fragmentUi;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.adapter.MessangerAdapter;
import com.offshoreindian.lokdon.adapter.SettingAdapter;
import com.offshoreindian.lokdon.customUi.SlidingTabLayout;
import com.offshoreindian.lokdon.framework.BaseFragment;
import com.offshoreindian.lokdon.utils.DebugUtil;


public class SettingFragment extends BaseFragment implements View.OnClickListener {


    SlidingTabLayout tabLayout;
    ViewPager vpPager;
    SettingAdapter settingAdapter;
    public SettingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting, null);
        tabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.setTextColor(getResources().getColor(R.color.white));
        tabLayout.setTextSelectedColor(getResources().getColor(R.color.colorPrimaryDark));

        vpPager = (ViewPager) view.findViewById(R.id.viewpager_setting);
        settingAdapter = new SettingAdapter(getChildFragmentManager(),vpPager);
        vpPager.setAdapter(settingAdapter);
        tabLayout.setViewPager(vpPager);
        tabLayout.setDistributeEvenly(true);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }


    @Override
    public void updateView() {
        super.updateView();


    }


}
