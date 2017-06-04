package com.offshoreindian.lokdon.fragmentUi;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.adapter.ContactListAdapter;
import com.offshoreindian.lokdon.adapter.MessangerAdapter;
import com.offshoreindian.lokdon.bean.ContactNoBean;
import com.offshoreindian.lokdon.bean.SmsBean;
import com.offshoreindian.lokdon.customUi.SlidingTabLayout;
import com.offshoreindian.lokdon.framework.BaseFragment;
import com.offshoreindian.lokdon.utils.Constants;
import com.offshoreindian.lokdon.utils.DebugUtil;

import java.util.ArrayList;
import java.util.List;


public class MessengerFragment extends BaseFragment implements View.OnClickListener{


    private ArrayList<ContactNoBean> contactNoArrayList;
    private SlidingTabLayout tabLayout ;
    private ContactListAdapter contactListAdapter;
    MessangerAdapter adapterViewPager;
    ViewPager vpPager;
    public MessengerFragment() {

//        if(applicationController.getValueFromPerference(Constants.PROVIDER_MESSENGER+"") == null) {
//            applicationController.putValueInPerference(Constants.PROVIDER_MESSENGER + "", "TRUE");
//            applicationController.getActivity().updateProviderListView();
//        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        applicationController.getActivity().showToolBar();
        View view = inflater.inflate(R.layout.fragment_messenger, null);


        tabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.setTextColor(getResources().getColor(R.color.light_grey2));
        tabLayout.setTextSelectedColor(getResources().getColor(R.color.white));

        vpPager = (ViewPager) view.findViewById(R.id.viewpager_message);
        adapterViewPager = new MessangerAdapter(getChildFragmentManager(),vpPager);
        vpPager.setAdapter(adapterViewPager);
        tabLayout.setViewPager(vpPager);
        tabLayout.setDistributeEvenly(true);
        return view;
    }
    @Override
    public void onClick(View v) {
            switch (v.getId())
            {
            }


    }



    @Override
    public void updateView() {

        super.updateView();
    }

    public List<SmsBean> getAllSms() {
        List<SmsBean> lstSms = new ArrayList<SmsBean>();
        SmsBean objSms = new SmsBean();
        Uri message = Uri.parse("content://sms/");
        ContentResolver cr = context.getContentResolver();

        Cursor c = cr.query(message, null, null, null, null);
        //startManagingCursor(c);
        int totalSMS = c.getCount();

        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {

                objSms = new SmsBean();
                objSms.setId(c.getString(c.getColumnIndexOrThrow("_id")));
                objSms.setAddress(c.getString(c
                        .getColumnIndexOrThrow("address")));
                objSms.setMsg(c.getString(c.getColumnIndexOrThrow("body")));
                objSms.setReadState(c.getString(c.getColumnIndex("read")));
                objSms.setTime(c.getString(c.getColumnIndexOrThrow("date")));
                if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                    objSms.setFolderName("inbox");
                } else {
                    objSms.setFolderName("sent");
                }
                DebugUtil.printLog(i+"  :::SMS "+objSms.toString());
                lstSms.add(objSms);
                c.moveToNext();
            }
        }
        // else {
        // throw new RuntimeException("You have no SMS");
        // }
        c.close();


        return lstSms;
    }


}
