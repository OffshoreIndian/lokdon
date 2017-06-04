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
import android.widget.AdapterView;
import android.widget.ListView;

import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.adapter.ContactListAdapter;
import com.offshoreindian.lokdon.adapter.InboxSmsListAdapter;
import com.offshoreindian.lokdon.adapter.TempInboxSmsListAdapter;
import com.offshoreindian.lokdon.bean.ContactNoBean;
import com.offshoreindian.lokdon.bean.SmsBean;
import com.offshoreindian.lokdon.bean.SmsDataListBean;
import com.offshoreindian.lokdon.bean.SmsThreadBean;
import com.offshoreindian.lokdon.framework.BaseFragment;
import com.offshoreindian.lokdon.utils.AppEvents;
import com.offshoreindian.lokdon.utils.DebugUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;


public class AllInboxMessageFragment extends BaseFragment implements View.OnClickListener {


    private ArrayList<ContactNoBean> contactNoArrayList;
    private ListView contactListView;
    private InboxSmsListAdapter inboxSmsListAdapter;
    private ViewPager vpPager;
    private LinkedHashMap<String, ArrayList<SmsBean>> smsBeanHashMap;
    private ArrayList<String> keyList;

    public AllInboxMessageFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_inbox, null);
        applicationController.getActivity().showToolBar();
        contactListView = (ListView) view.findViewById(R.id.sms_list_view);

        smsBeanHashMap = createSmsThread(getAllSms());
        keyList = new ArrayList<String>(smsBeanHashMap.keySet());

        if (inboxSmsListAdapter == null)
            inboxSmsListAdapter = new InboxSmsListAdapter(context,smsBeanHashMap);

//            inboxSmsListAdapter = new TempInboxSmsListAdapter(context, getAllSms());

        contactListView.setAdapter(inboxSmsListAdapter);
        inboxSmsListAdapter.notifyDataSetChanged();
        ;
//
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //showSMSPopup(contactNoArrayList.get(position));
//                ArrayList<SmsBean> data = smsBeanHashMap.get(keyList.get(position));
                applicationController.handleEvent(AppEvents.EVENT_SHOW_MESSAGE_SCREEN, new SmsDataListBean(smsBeanHashMap.get(keyList.get(position))));
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


    public LinkedHashMap<String, ArrayList<SmsBean>> createSmsThread(ArrayList<SmsBean> list) {
        LinkedHashMap<String, ArrayList<SmsBean>> hashMap = new LinkedHashMap<String, ArrayList<SmsBean>>();
        for (int i = 0; i < list.size(); i++) {

            if (hashMap.containsKey(list.get(i).getAddress())) {
                ArrayList<SmsBean> array = hashMap.get(list.get(i).getAddress());
                array.add(list.get(i));


            } else
            {
                ArrayList<SmsBean> listTemp = new ArrayList<SmsBean>();
                listTemp.add(list.get(i));
                hashMap.put(list.get(i).getAddress(), listTemp);
            }
        }

        List<String> l = new ArrayList<String>(hashMap.keySet());

        return hashMap;

    }

    private void sortAllSmsByDate(ArrayList<SmsBean> list) {
        Collections.sort(list, new Comparator<SmsBean>() {
            @Override
            public int compare(SmsBean o1, SmsBean o2) {
                return o1.getTime().compareTo(o2.getTime());

            }
        });

    }


    private void sortAllSmsByDate1(ArrayList<SmsBean> list) {
       final DateFormat f = new SimpleDateFormat("dd:MM:yy HH:mm:ss");

        Collections.sort(list, new Comparator<SmsBean>() {
            @Override
            public int compare(SmsBean o1, SmsBean o2) {
                 try {
                    return f.parse(o1.getTimeInDate()).compareTo(f.parse(o2.getTimeInDate()));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });

    }
    public ArrayList<SmsBean> getAllSms() {
        ArrayList<SmsBean> lstSms = new ArrayList<SmsBean>();
        SmsBean objSms = new SmsBean();
        Uri message = Uri.parse("content://sms/");
        ContentResolver cr = context.getContentResolver();

        Cursor c = cr.query(message, null, null, null, null);
        //startManagingCursor(c);
        int totalSMS = c.getCount();

        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {

                try {

                    objSms = new SmsBean();
                    objSms.setId(c.getString(c.getColumnIndexOrThrow("_id")));
                    objSms.setAddress(c.getString(c
                            .getColumnIndexOrThrow("address")));
                    objSms.setMsg(c.getString(c.getColumnIndexOrThrow("body")));
                    objSms.setReadState(c.getString(c.getColumnIndex("read")));
                    objSms.setTime(c.getString(c.getColumnIndexOrThrow("date")));
                    objSms.setSmsType(Integer.parseInt(c.getString(c.getColumnIndexOrThrow("type"))));
                    if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                        objSms.setFolderName("inbox");
                    } else {
                        objSms.setFolderName("sent");
                    }
//                      DebugUtil.printLog(i + "  :::SMS " + objSms.toString());
                   // DebugUtil.printLog(i + "  :::SMS " + objSms.getAddress());

                    lstSms.add(objSms);
                }catch (Exception e)
                {
                    DebugUtil.printExceptionLog(" parse message ",e);
                }


                c.moveToNext();
            }
        }
        sortAllSmsByDate(lstSms);
        ArrayList<SmsBean> lstSmsTemp = new ArrayList<SmsBean>();

        for (int i = lstSms.size()-1;i>=0;i--)
        {
            lstSmsTemp.add(lstSms.get(i));
           // DebugUtil.printLog( i+"  obj "+lstSms.get(i).getTimeInDate()+" mes "+lstSms.get(i).getAddress());
        }

        // else {
        // throw new RuntimeException("You have no SMS");
        // }
        c.close();


        return lstSmsTemp;
    }


}
