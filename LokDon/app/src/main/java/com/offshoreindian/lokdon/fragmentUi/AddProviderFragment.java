package com.offshoreindian.lokdon.fragmentUi;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.adapter.DrawerProviderListAdapter;
import com.offshoreindian.lokdon.bean.DrawerProviderBean;
import com.offshoreindian.lokdon.framework.BaseFragment;
import com.offshoreindian.lokdon.utils.AppEvents;
import com.offshoreindian.lokdon.utils.Constants;
import com.offshoreindian.lokdon.utils.DebugUtil;

import java.util.ArrayList;


public class AddProviderFragment extends BaseFragment implements View.OnClickListener {



    private ProgressBar progressBar;
    private ListView providerListView;
    private ArrayList<DrawerProviderBean> providerBeanArrayList;

    public AddProviderFragment() {
        providerBeanArrayList = new ArrayList<DrawerProviderBean>();
        for (int i = 0;i< Constants.provideName.length;i++)
        {
            providerBeanArrayList.add(new DrawerProviderBean(R.mipmap.google_drive,Constants.provideName[i],Constants.provideTagId[i]));

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_provide, null);
         applicationController.getActivity().showAndHideFloatIcon(View.GONE);
        applicationController.getActivity().showToolBar();
        progressBar =(ProgressBar) view.findViewById(R.id.loading_progressBar);
        providerListView = (ListView) view.findViewById(R.id.provider_listview);
        providerListView.setAdapter(new DrawerProviderListAdapter(context,providerBeanArrayList));
        providerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                applicationController.handleAppLaunchEvent(providerBeanArrayList.get(position).getTagId());

            }
        });
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        applicationController.getActivity().showAndHideFloatIcon(View.VISIBLE);
    }
}
