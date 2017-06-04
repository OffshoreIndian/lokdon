package com.offshoreindian.lokdon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.bean.DrawerProviderBean;
import com.offshoreindian.lokdon.bean.SmsBean;
import com.offshoreindian.lokdon.encryption.AESEncryption;
import com.offshoreindian.lokdon.framework.ApplicationController;
import com.offshoreindian.lokdon.utils.Constants;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by praveshkumar on 14/10/16.
 */

public class DrawerProviderListAdapter extends BaseAdapter
{
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<DrawerProviderBean> drawerProviderBeanArrayList;

    public DrawerProviderListAdapter(Context context, ArrayList<DrawerProviderBean> list)
    {
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.drawerProviderBeanArrayList = list;


    }
    @Override
    public int getCount() {
        return drawerProviderBeanArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return drawerProviderBeanArrayList.get(position);
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
             convertView = layoutInflater.inflate(R.layout.item_provider_list, null);
             holder.icon = (ImageView) convertView.findViewById(R.id.list_icon);
             holder.name = (TextView) convertView.findViewById(R.id.list_name);
             holder.addButton =(ImageView) convertView.findViewById(R.id.added);
             convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.name.setText(drawerProviderBeanArrayList.get(position).getProviderName());
        holder.icon.setImageResource(drawerProviderBeanArrayList.get(position).getIconId());

        if(ApplicationController.getInstance().getValueFromPerference(drawerProviderBeanArrayList.get(position).getTagId()+"") !=null) {
            holder.addButton.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.addButton.setVisibility(View.VISIBLE);
        }

            return convertView;
    }

    public static class ViewHolder {
        public ImageView icon , addButton;
        public TextView name;

    }


    public ArrayList<DrawerProviderBean> getDrawerProviderBeanArrayList() {
        return drawerProviderBeanArrayList;
    }

    public void setDrawerProviderBeanArrayList(ArrayList<DrawerProviderBean> drawerProviderBeanArrayList) {
        this.drawerProviderBeanArrayList = drawerProviderBeanArrayList;
    }
}
