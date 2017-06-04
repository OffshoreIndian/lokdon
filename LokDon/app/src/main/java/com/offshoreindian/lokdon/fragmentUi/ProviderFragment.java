package com.offshoreindian.lokdon.fragmentUi;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.framework.BaseFragment;
import com.offshoreindian.lokdon.utils.DebugUtil;


public class ProviderFragment extends BaseFragment implements View.OnClickListener {


    private TextView no_provide_tv;
    private ListView added_provider_list;
     private ProgressBar progressBar;

    public ProviderFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_provide, null);
        applicationController.getActivity().showToolBar();
        applicationController.getActivity().showAndHideFloatIcon(View.VISIBLE);
        no_provide_tv = (TextView) view.findViewById(R.id.no_provide_tv);
        added_provider_list = (ListView) view.findViewById(R.id.added_provider_list);
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
