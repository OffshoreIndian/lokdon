package com.offshoreindian.lokdon.fragmentUi;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.activity.LokdonActivity;
import com.offshoreindian.lokdon.activity.NotifyPopUpActivity;
import com.offshoreindian.lokdon.adapter.ContactListAdapter;
import com.offshoreindian.lokdon.adapter.MessangerAdapter;
import com.offshoreindian.lokdon.bean.ContactNoBean;
import com.offshoreindian.lokdon.bean.SmsBean;
import com.offshoreindian.lokdon.customUi.SlidingTabLayout;
import com.offshoreindian.lokdon.database.OtherAppEventDataBase;
import com.offshoreindian.lokdon.encryption.SmsEncryption;
import com.offshoreindian.lokdon.framework.BaseFragment;
import com.offshoreindian.lokdon.utils.Constants;
import com.offshoreindian.lokdon.utils.DebugUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;




public class AllContactsFragment extends BaseFragment implements View.OnClickListener {


    private ArrayList<ContactNoBean> contactNoArrayList, tempContactList, searchContactList;
    private ListView contactListView;
    private ContactListAdapter contactListAdapter;
    private ViewPager vpPager;
    private Dialog dialog;
    private Button okButton, cancelButton;
    private TextView messageTextview;
    private ApplicationInfo applicationInfo;
    private PackageManager packageManager;
    private CheckBox donot_ask;
    private boolean do_not_ask = false;
    private ImageView crossImage;
    private EditText sms_editbox;
    private EditText searchEditBox;

    public AllContactsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_contact_list, null);
        applicationController.getActivity().showToolBar();
        contactListView = (ListView) view.findViewById(R.id.contact_listview);
        searchEditBox = (EditText) view.findViewById(R.id.contact_search);

        if (contactNoArrayList == null) {
            contactNoArrayList = new ArrayList<ContactNoBean>();
            Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            while (phones.moveToNext()) {

                ContactNoBean bean = new ContactNoBean();
                bean.setDisplayName(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                bean.setPhoneNo(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                contactNoArrayList.add(bean);
            }
            phones.close();


        }
        Collections.sort(contactNoArrayList, new Comparator<ContactNoBean>() {
            @Override
            public int compare(ContactNoBean lhs, ContactNoBean rhs) {
                return lhs.getDisplayName().compareToIgnoreCase(rhs.getDisplayName());

            }
        });

        tempContactList = contactNoArrayList;
        searchContactList = new ArrayList<ContactNoBean>();

        if (contactListAdapter == null)
            contactListAdapter = new ContactListAdapter(context);

        contactListAdapter.setContactNoArrayList(contactNoArrayList);
        contactListView.setAdapter(contactListAdapter);

        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DebugUtil.printLog(contactNoArrayList.get(position).getDisplayName()+" Clicked pos"+position);
                showSMSPopup(contactListAdapter.getContactNoArrayList().get(position));
            }
        });
        contactListAdapter.notifyDataSetChanged();
        contactListView.setTextFilterEnabled(true);


        searchEditBox.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
//                DebugUtil.printLog(" CharSequenc=" + cs + "=ccc-" + cs.length());
                if (cs.length() > 0) {
                    contactListAdapter.getFilter().filter(cs);
                } else {
                    contactListAdapter.setContactNoArrayList(contactNoArrayList);
                    contactListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }


    }


    public void showSMSPopup(  ContactNoBean bean) {
        final ContactNoBean bean1 = bean;
        dialog = null;
        if (dialog == null) {
            dialog = new Dialog(context, R.style.Dialog_theme);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.send_sms_popup);
            //dialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            Drawable d = new ColorDrawable(Color.BLACK);
            d.setAlpha(130);
            dialog.getWindow().setBackgroundDrawable(d);

            messageTextview = (TextView) dialog.findViewById(R.id.sender_name);
            messageTextview.setText(bean1.getDisplayName() + " (" + bean1.getPhoneNo() + ")");
            sms_editbox = (EditText) dialog.findViewById(R.id.sms_editbox);

            crossImage = (ImageView) dialog.findViewById(R.id.cross_button);
            crossImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();

                }
            });

            okButton = (Button) dialog.findViewById(R.id.ok_button);
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     String smsText = SmsEncryption.getInstance().getEncryptSms( sms_editbox.getText().toString(),bean1.getPhoneNo());
                    sendSMSMessage(bean1.getPhoneNo(), smsText+ Constants.MES_LOKDON_TEXT);
                    dialog.dismiss();


                }
            });


        }

        dialog.show();
    }

    protected void sendSMSMessage(String number, String text) {


        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, text, null, null);
            applicationController.showToastMessages("SMS Sent.");

        } catch (Exception e) {

            applicationController.showToastMessages("SMS failed, please try again.");
            e.printStackTrace();
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
              //  DebugUtil.printLog(i + "  :::SMS " + objSms.toString());
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
