package com.offshoreindian.lokdon.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.offshoreindian.lokdon.fragmentUi.AllContactsFragment;
import com.offshoreindian.lokdon.fragmentUi.AllInboxMessageFragment;

/**
 * Created by praveshkumar on 15/10/16.
 */

public class MessangerAdapter extends FragmentPagerAdapter {


    private String title[] = {"All Contacts", "All SMS"};
    public static final int ALL_CONTACTS = 0;
    public static final int ALL_SMS = 1;
    private ViewPager pager;

    public MessangerAdapter(FragmentManager fragmentManager, ViewPager vpPager) {
        super(fragmentManager);
        this.pager = vpPager;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case ALL_CONTACTS: {
                return new AllContactsFragment();

            }
            case ALL_SMS: {
                return new AllInboxMessageFragment();
            }

        }
        return null;
    }

    @Override
    public int getCount() {
        return title.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];

    }
}