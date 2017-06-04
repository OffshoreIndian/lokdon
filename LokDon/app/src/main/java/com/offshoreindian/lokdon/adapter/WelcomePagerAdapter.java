package com.offshoreindian.lokdon.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.offshoreindian.lokdon.fragmentUi.LoginFragment;
import com.offshoreindian.lokdon.fragmentUi.WelcomeAccountFragment;
import com.offshoreindian.lokdon.fragmentUi.WelcomeBrowserFragment;
import com.offshoreindian.lokdon.fragmentUi.WelcomeEncryptionFragment;
import com.offshoreindian.lokdon.fragmentUi.WelcomeProviderFragment;
import com.offshoreindian.lokdon.fragmentUi.WelcomeReadyFragment;

/**
 * Created by praveshkumar on 21/11/16.
 */

public class WelcomePagerAdapter extends FragmentPagerAdapter {
    public WelcomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
            {
                return  new WelcomeAccountFragment();
            }
            case 1:
            {
                return new WelcomeProviderFragment();
            }
            case 2:
            {
                return  new WelcomeEncryptionFragment();
            }

//            case 3:
//            {
//                return  new WelcomeBrowserFragment();
//            }
            case 3:
            {
                return  new WelcomeReadyFragment();
            }

        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
