package com.offshoreindian.lokdon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.bean.SmsBean;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by praveshkumar on 14/10/16.
 */

public class TempInboxSmsListAdapter extends BaseAdapter
{
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<SmsBean> smsBeanHashMap;
    private ArrayList<String> keyList;

    public TempInboxSmsListAdapter(Context context,ArrayList<SmsBean> hashMap)
    {
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.smsBeanHashMap = hashMap;
          //keyList = new ArrayList<String>(smsBeanHashMap.keySet());

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
         SmsBean beanList = (smsBeanHashMap.get(position));

        holder.contactName.setText(beanList.getAddress());
        holder.contactNo.setText(beanList.getMsg());



        return convertView;

    }

    public static class ViewHolder {
        public TextView contactName;
        public TextView contactNo;

    }



}
