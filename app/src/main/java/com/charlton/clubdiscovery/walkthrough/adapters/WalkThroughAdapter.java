package com.charlton.clubdiscovery.walkthrough.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cj on 10/15/16.
 */
public class WalkThroughAdapter extends FragmentStatePagerAdapter {
    List<Fragment> fragments = new ArrayList<>();

    public WalkThroughAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragments(Fragment... fragments){
        for (Fragment f: fragments){
            this.fragments.add(f);
        }
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
