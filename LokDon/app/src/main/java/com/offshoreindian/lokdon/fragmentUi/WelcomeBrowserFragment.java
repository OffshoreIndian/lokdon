package com.offshoreindian.lokdon.fragmentUi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.adapter.WelcomePagerAdapter;
import com.offshoreindian.lokdon.framework.BaseFragment;


public class WelcomeBrowserFragment extends BaseFragment implements View.OnClickListener {


    public WelcomeBrowserFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_browser_wel, null);

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


    }

    @Override
    public void updateView() {
        super.updateView();


    }


}
