package com.fitness;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

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
       ;
    }

    private void initView() {
        navigationBottom = getSupportActionBar();
        navigationView = findViewById(R.id.bottomNavigation);
        frameLayout = findViewById(R.id.frameLayoutMainActivity);
        loadFragmentt(new Fragment_HomeScreen());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch (menuItem.getItemId()) {
                case R.id.navigation_shop:
                    loadFragmentt(new Fragment_HomeScreen());
                    return true;
                case R.id.navigation_gifts:
                    loadFragmentt(new Fragment_CustomWorkScreen());
                    return true;
                case R.id.navigation_cart:
                    loadFragmentt(new Fragment_HomeScreen());
                    return true;
                case R.id.navigation_profile:
                    loadFragmentt(new Fragment_HomeScreen());
                    return true;
            }
            return false;
        }
    };
    public void loadFragmentt(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutMainActivity, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
