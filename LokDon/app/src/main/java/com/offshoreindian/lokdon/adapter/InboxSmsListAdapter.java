package com.offshoreindian.lokdon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.bean.ContactNoBean;
import com.offshoreindian.lokdon.bean.SmsBean;
import com.offshoreindian.lokdon.encryption.AESEncryption;
import com.offshoreindian.lokdon.encryption.SmsEncryption;
import com.offshoreindian.lokdon.utils.Constants;
import com.offshoreindian.lokdon.utils.DebugUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

/**
 * Created by praveshkumar on 14/10/16.
 */

public class InboxSmsListAdapter extends BaseAdapter
{
    private LayoutInflater layoutInflater;
    private Context context;
    private LinkedHashMap<String,ArrayList<SmsBean>> smsBeanHashMap;
    private ArrayList<String> keyList;

    public InboxSmsListAdapter(Context context, LinkedHashMap<String,ArrayList<SmsBean>> hashMap)
    {
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.smsBeanHashMap = hashMap;
          keyList = new ArrayList<String>(smsBeanHashMap.keySet());

    }
    @Override
    public int getCount() {
        return smsBeanHashMap.size();
    }

    @Override
    public Object getItem(int position) {
        return smsBeanHashMap.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    ViewHolder holder = null;

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
         if (convertView == null) {
             holder = new ViewHolder();
             convertView = layoutInflater.inflate(R.layout.item_inbox_mes_list, null);
             holder.contactName = (TextView) convertView.findViewById(R.id.contact_name);
             holder.contactNo = (TextView) convertView.findViewById(R.id.contact_number);

             convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.contactName.setText(keyList.get(position));
        ArrayList<SmsBean> beanList = smsBeanHashMap.get(keyList.get(position));

        if (beanList.get(0).getMsg().contains(Constants.MES_LOKDON_TEXT)) {
            //AESEncryption.getInstance().setKey(Constants.ENCRYPTION_KEY);
//            String str = beanList.get(0).getMsg().substring(0, beanList.get(0).getMsg().lastIndexOf("=") + 1);
            String str=   beanList.get(0).getMsg().replace(Constants.MES_LOKDON_TEXT,"");
            DebugUtil.printLog("str  str   "+str);
           //  AESEncryption.getInstance().decrypt(str);
            holder.contactNo.setText(SmsEncryption.getInstance().decryptSms(str));
        } else {
            holder.contactNo.setText(beanList.get(0).getMsg());

        }
      //  holder.contactNo.setText(beanList.get(0).getMsg());



        return convertView;

    }

    public static class ViewHolder {
        public TextView contactName;
        public TextView contactNo;

    }



}
