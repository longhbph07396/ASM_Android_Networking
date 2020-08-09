package com.hblong.assigment.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hblong.assigment.fragment.CategoryFragment;
import com.hblong.assigment.fragment.FavouriteFragment;

public class HomeViewPagerAdapter extends FragmentPagerAdapter {
    public HomeViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new FavouriteFragment();
            case 2:
                return new CategoryFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
