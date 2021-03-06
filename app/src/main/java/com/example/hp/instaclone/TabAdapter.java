package com.example.hp.instaclone;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.hp.instaclone.Fragment.ProfileTabFragment;
import com.example.hp.instaclone.Fragment.UsersTabFragment;

public class TabAdapter extends FragmentPagerAdapter {


    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int tabPosition) {
        switch (tabPosition) {
            case 0:
                ProfileTabFragment profileTabFragment = new ProfileTabFragment();
                return profileTabFragment;

            case 1:
                UsersTabFragment usersTabFragment = new UsersTabFragment();
                return usersTabFragment;


            //Alternative way without new variable
            case 2:
                return new UsersTabFragment();


            //Need to create default value
            default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Profile";

            case 1:
                return "Users";

            case 2:
                return "Photos";

            default:
                return null;
        }
    }
}

