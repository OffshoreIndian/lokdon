package com.offshoreindian.lokdon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.bean.ContactNoBean;
import com.offshoreindian.lokdon.utils.DebugUtil;

import java.util.ArrayList;

/**
 * Created by praveshkumar on 14/10/16.
 */

public class ContactListAdapter extends BaseAdapter implements Filterable
{
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<ContactNoBean> contactNoArrayList;
     private ArrayList<ContactNoBean> mDisplayedValues;
    public ContactListAdapter(Context context)
    {
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
     }
    @Override
    public int getCount() {
        return contactNoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactNoArrayList.get(position);
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
             convertView = layoutInflater.inflate(R.layout.item_contact_list, null);
             holder.contactName = (TextView) convertView.findViewById(R.id.contact_name);
             holder.contactNo = (TextView) convertView.findViewById(R.id.contact_number);

             convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.contactName.setText(contactNoArrayList.get(position).getDisplayName());
        holder.contactNo.setText(contactNoArrayList.get(position).getPhoneNo());

        return convertView;

    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                mDisplayedValues = (ArrayList<ContactNoBean>) results.values; // has the filtered values
                    setContactNoArrayList(mDisplayedValues);
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<ContactNoBean> FilteredArrList = new ArrayList<ContactNoBean>();

                if (contactNoArrayList == null) {
                    contactNoArrayList = new ArrayList<ContactNoBean>(mDisplayedValues); // saves the original data in mOriginalValues
                }


                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = contactNoArrayList.size();
                    results.values = contactNoArrayList;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < contactNoArrayList.size(); i++) {
                        String data = contactNoArrayList.get(i).getDisplayName();
                         if (data.toLowerCase().startsWith(constraint.toString())) {

                            ContactNoBean bean = new ContactNoBean();
                            bean.setDisplayName(contactNoArrayList.get(i).getDisplayName());
                            bean.setPhoneNo(contactNoArrayList.get(i).getPhoneNo());
                            bean.setPhotoUrl(contactNoArrayList.get(i).getPhotoUrl());
                            FilteredArrList.add(bean);
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }


    public static class ViewHolder {
        public TextView contactName;
        public TextView contactNo;

    }


    public ArrayList<ContactNoBean> getContactNoArrayList() {
        return contactNoArrayList;
    }

    public void setContactNoArrayList(ArrayList<ContactNoBean> contactNoArrayList) {
        this.contactNoArrayList = contactNoArrayList;
    }


}
