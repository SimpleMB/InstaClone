package com.example.hp.instaclone;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.hp.instaclone.Fragment.ProfileTabFragment;
import com.example.hp.instaclone.Fragment.SharePhotoTabFragment;
import com.example.hp.instaclone.Fragment.UsersTabFragment;


public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Toolbar toolbar;
    private SecondAdapter tabAdapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Super Duper");

        viewPager = findViewById(R.id.viewPager);
        toolbar = findViewById(R.id.myToolbar);
        tabLayout = findViewById(R.id.tabLayout);
        tabAdapter = new SecondAdapter(getSupportFragmentManager());

        tabAdapter.addFragment(new ProfileTabFragment(), "Profile");
        tabAdapter.addFragment(new UsersTabFragment(), "Users");
        tabAdapter.addFragment(new SharePhotoTabFragment(), "Photos");

        setSupportActionBar(toolbar);
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager, true);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_group);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_group);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_group);

    }
}
