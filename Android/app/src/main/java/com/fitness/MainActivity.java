package com.fitness;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    TabLayout tabLayout;
    private List<Fragment> listFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        //--------Init Fragment--------------------------
        Fragment_HomeScreen homeScreen = new Fragment_HomeScreen();
        Fragment_CustomWorkScreen secondScreen = new Fragment_CustomWorkScreen();
        FragmentListPractice listPractice = new FragmentListPractice();

        //-------Add list fragment to display------------
        listFragment.add(homeScreen);
        listFragment.add(secondScreen);
        listFragment.add(listPractice);

        //-------Set ViewPager--------------------------
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(),listFragment);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        //-------Set button TabLayout-----------------
        tabLayout.setupWithViewPager(viewPager);

        //--------Set icon Tablayout------------
        for(int i = 0; i<tabLayout.getTabCount();i++) {
            tabLayout.getTabAt(i).setIcon(adapter.getTabIcon()[i]);
        }
    }

    private void initView() {
        viewPager = findViewById(R.id.viewPagerHome);
        tabLayout= (TabLayout) findViewById(R.id.tabLayout);
        listFragment = new ArrayList<>();
    }
}
