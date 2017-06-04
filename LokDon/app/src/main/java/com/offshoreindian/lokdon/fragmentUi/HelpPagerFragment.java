package com.offshoreindian.lokdon.fragmentUi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.framework.BaseFragment;


public class HelpPagerFragment extends BaseFragment implements View.OnClickListener {


    public HelpPagerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_help_pager, null);
     //   applicationController.getActivity().showHomeScreenLogo();


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
