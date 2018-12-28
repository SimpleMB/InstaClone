package com.example.hp.instaclone;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SecondAdapter extends FragmentPagerAdapter {

    List<Fragment> lstFragments = new ArrayList<>();
    List<String> lstTitles = new ArrayList<>();


    public SecondAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return lstFragments.get(position);
    }

    @Override
    public int getCount() {
        return lstFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return lstTitles.get(position);
    }

    public void addFragment (Fragment fragment, String title) {

        lstFragments.add(fragment);
        lstTitles.add(title);

    }
}
