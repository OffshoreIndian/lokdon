package com.offshoreindian.lokdon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.bean.GoogleDriveFileBean;
import com.offshoreindian.lokdon.bean.SmsBean;
import com.offshoreindian.lokdon.encryption.AESEncryption;
import com.offshoreindian.lokdon.utils.Constants;

import java.util.ArrayList;

/**
 * Created by praveshkumar on 14/10/16.
 */

public class GoogleDriveFileListAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<GoogleDriveFileBean> googleDriveFileList;
    private ArrayList<String> keyList;

    public GoogleDriveFileListAdapter(Context context, ArrayList<GoogleDriveFileBean> hashMap) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.googleDriveFileList = hashMap;

    }

    @Override
    public int getCount() {
        return googleDriveFileList.size();
    }

    @Override
    public Object getItem(int position) {
        return googleDriveFileList.get(position);
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
            convertView = layoutInflater.inflate(R.layout.item_inbox_mes_list, null);
            holder.contactName = (TextView) convertView.findViewById(R.id.contact_name);
            holder.contactNo = (TextView) convertView.findViewById(R.id.contact_number);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.contactName.setText(googleDriveFileList.get(position).getFileName());
        holder.contactNo.setText(googleDriveFileList.get(position).getFileSize());


        return convertView;

    }

    public static class ViewHolder {
        public TextView contactName;
        public TextView contactNo;

    }


}
