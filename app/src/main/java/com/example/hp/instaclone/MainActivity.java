package com.example.hp.instaclone;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Toolbar toolbar;
    private TabAdapter tabAdapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Flat Chat App :)");

        viewPager = findViewById(R.id.viewPager);
        toolbar = findViewById(R.id.myToolbar);
        tabLayout = findViewById(R.id.tabLayout);
        tabAdapter = new TabAdapter(getSupportFragmentManager());

        setSupportActionBar(toolbar);
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager, true);

    }
}
