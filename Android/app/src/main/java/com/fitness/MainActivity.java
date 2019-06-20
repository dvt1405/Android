package com.fitness;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.fitness.customwork.Fragment_CustomWorkScreen;
import com.fitness.homescreen.Fragment_HomeScreen;
import com.fitness.locked.Fragment_Locked_Practice;

import sqlite.GuideDAO;
import sqlite.PracticeDAO;
import sqlite.PracticeGroupDAO;

public class MainActivity extends AppCompatActivity {
    private ActionBar navigationBottom;
    BottomNavigationView navigationView;
    private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        navigationView.setOnNavigationItemReselectedListener(onNavigationItemReselectedListener);

    }
    private void initView() {
        navigationBottom = getSupportActionBar();
        navigationView = findViewById(R.id.bottomNavigation);
        frameLayout = findViewById(R.id.frameLayoutMainActivity);

        //create Dèault data
        new GuideDAO(getBaseContext()).onCreateDefaultGuideDatabase();
        new PracticeDAO(this.getBaseContext()).createDefaultDataPractice();
        new PracticeGroupDAO(this.getBaseContext()).createDefaultGroupData();
        loadFragmentt(new Fragment_HomeScreen());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.navigation_shop:
                    loadFragmentt(new Fragment_HomeScreen());
                    return true;
                case R.id.navigation_gifts:
                    loadFragmentt(new Fragment_CustomWorkScreen());
                    return true;
                case R.id.navigation_cart:
                    loadFragmentt(new Fragment_Locked_Practice());
                    return true;
                case R.id.navigation_profile:
                    loadFragmentt(new Fragment_HomeScreen());
                    return true;
            }
            return false;
        }
    };

    private BottomNavigationView.OnNavigationItemReselectedListener onNavigationItemReselectedListener
            = new BottomNavigationView.OnNavigationItemReselectedListener() {
        @Override
        public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
            Log.e("Reselected; ", menuItem.getItemId()+"");
        }
    };

    public void loadFragmentt(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutMainActivity, fragment);
        fragmentTransaction.addToBackStack(fragment.getTag());

        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
