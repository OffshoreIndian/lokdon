package com.offshoreindian.lokdon.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.offshoreindian.lokdon.fragmentUi.AccountFragment;
import com.offshoreindian.lokdon.fragmentUi.GeneralFragment;
import com.offshoreindian.lokdon.fragmentUi.HelpAboutFragment;
import com.offshoreindian.lokdon.fragmentUi.HelpPagerFragment;
import com.offshoreindian.lokdon.fragmentUi.MediaFragment;

/**
 * Created by praveshkumar on 15/10/16.
 */

public class HelpAdapter extends FragmentPagerAdapter {


    private String title[] = {"Help", "About"};
    public static final int HELP = 0;
    public static final int ABOUT = 1;

    private ViewPager pager;

    public HelpAdapter(FragmentManager fragmentManager, ViewPager vpPager) {
        super(fragmentManager);
        this.pager = vpPager;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case HELP: {
                return new HelpPagerFragment();
            }
            case ABOUT: {
                return new HelpAboutFragment();
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