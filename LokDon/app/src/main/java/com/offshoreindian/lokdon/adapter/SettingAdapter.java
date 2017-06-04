package com.offshoreindian.lokdon.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.offshoreindian.lokdon.fragmentUi.AccountFragment;
import com.offshoreindian.lokdon.fragmentUi.AllContactsFragment;
import com.offshoreindian.lokdon.fragmentUi.AllInboxMessageFragment;
import com.offshoreindian.lokdon.fragmentUi.GeneralFragment;
import com.offshoreindian.lokdon.fragmentUi.MediaFragment;

/**
 * Created by praveshkumar on 15/10/16.
 */

public class SettingAdapter extends FragmentPagerAdapter {


    private String title[] = {"General", "Account","Media"};
    public static final int GENERAL = 0;
    public static final int ACCOUNT = 1;
    public static final int MEDIA = 2;

    private ViewPager pager;

    public SettingAdapter(FragmentManager fragmentManager, ViewPager vpPager) {
        super(fragmentManager);
        this.pager = vpPager;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case GENERAL: {
                return new GeneralFragment();

            }
            case ACCOUNT: {
                return new AccountFragment();
            }
            case MEDIA: {
                return new MediaFragment();
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