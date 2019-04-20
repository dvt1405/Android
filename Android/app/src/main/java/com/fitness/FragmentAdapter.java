package com.fitness;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class FragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> listFragment;
    private List<String> listTitle;
    private int[] tabIcon = {R.drawable.icons8home24,
            R.drawable.icons8plus24,
            R.drawable.icons8bookmark24,
            R.drawable.icons8user24};
    public FragmentAdapter(FragmentManager fm, List<Fragment> listFragment) {
        super(fm);
        this.listFragment = listFragment;
    }

    @Override
    public Fragment getItem(int i) {
        return this.listFragment.get(i);
    }

    @Override
    public int getCount() {
        return this.listFragment.size();
    }

    public List<Fragment> getListFragment() {
        return listFragment;
    }

    public void setListFragment(List<Fragment> listFragment) {
        this.listFragment = listFragment;
    }

    public List<String> getListTitle() {
        return listTitle;
    }

    public void setListTitle(List<String> listTitle) {
        this.listTitle = listTitle;
    }

    public int[] getTabIcon() {
        return tabIcon;
    }

    public void setTabIcon(int[] tabIcon) {
        this.tabIcon = tabIcon;
    }
}
