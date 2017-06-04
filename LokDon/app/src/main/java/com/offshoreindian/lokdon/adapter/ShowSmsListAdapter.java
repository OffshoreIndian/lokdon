package com.offshoreindian.lokdon.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.bean.SmsBean;
import com.offshoreindian.lokdon.encryption.AESEncryption;
import com.offshoreindian.lokdon.encryption.SmsEncryption;
import com.offshoreindian.lokdon.utils.Constants;
import com.offshoreindian.lokdon.utils.DebugUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by praveshkumar on 14/10/16.
 */

public class ShowSmsListAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    ArrayList<SmsBean> smsBeanArrayList;

    public ShowSmsListAdapter(Context context, ArrayList<SmsBean> list) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.smsBeanArrayList = list;
        sortAllSmsByDate1(smsBeanArrayList);

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

    @Override
    public int getCount() {
        return smsBeanArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return smsBeanArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    ViewHolder holder = null;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_inbox_show_mes_list, null);
            holder.contactName = (TextView) convertView.findViewById(R.id.message);
            holder.contactNo = (TextView) convertView.findViewById(R.id.time_sms);

            holder.messageParentLayout = (LinearLayout) convertView.findViewById(R.id.message_parent_layout);
            holder.messageLayout = (LinearLayout) convertView.findViewById(R.id.message_layout);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (smsBeanArrayList.get(position).getMsg().contains(Constants.MES_LOKDON_TEXT)) {
            AESEncryption.getInstance().setKey(Constants.ENCRYPTION_KEY);
         //   String str = smsBeanArrayList.get(position).getMsg().substring(0, smsBeanArrayList.get(position).getMsg().lastIndexOf("=") + 1);
             //AESEncryption.getInstance().decrypt(str);
            String str=  smsBeanArrayList.get(position).getMsg().replace(Constants.MES_LOKDON_TEXT,"");
//            SmsEncryption.getInstance().decryptSms(str);
            holder.contactName.setText(SmsEncryption.getInstance().decryptSms(str));
        } else {
            holder.contactName.setText(smsBeanArrayList.get(position).getMsg());

        }
        holder.contactNo.setText(convertTime(smsBeanArrayList.get(position).getTime()));


        if (smsBeanArrayList.get(position).getSmsType() == 1)// means inbox
        {

            holder.messageParentLayout.setGravity(Gravity.RIGHT);
            holder.messageLayout.setBackgroundResource(R.color.light_red);
        } else if (smsBeanArrayList.get(position).getSmsType() == 2)// means sent
        {
            holder.messageParentLayout.setGravity(Gravity.LEFT);
            holder.messageLayout.setBackgroundResource(R.color.blue_semi_transparent);
        }

        return convertView;

    }

    public static class ViewHolder {
        public TextView contactName, sentMsgText;
        public TextView contactNo, sentMesTime;
        public LinearLayout messageParentLayout, messageLayout;

    }


    private String convertTime(String time) {
        Date currentDate = new Date(Long.parseLong(time));
        DateFormat df = new SimpleDateFormat("dd:MM:yy HH:mm:ss");
        return df.format(currentDate);
    }


}
