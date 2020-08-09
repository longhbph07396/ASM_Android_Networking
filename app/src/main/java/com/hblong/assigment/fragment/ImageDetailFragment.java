package com.hblong.assigment.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.hblong.assigment.R;
import com.hblong.assigment.activity.HomeActivity;
import com.hblong.assigment.adapter.ImageViewPagerAdapter;

public class ImageDetailFragment extends Fragment {
    private ViewPager viewPager;
    private int code;

    public ImageDetailFragment(int code) {
        this.code = code;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_image, container, false);


        viewPager = view.findViewById(R.id.viewPager);
        if (code == FavouriteFragment.FRAGMENT_CODE) {
            ImageViewPagerAdapter imageViewPagerAdapter = new ImageViewPagerAdapter(getActivity().getSupportFragmentManager(), HomeActivity.imageFavourites);
            viewPager.setAdapter(imageViewPagerAdapter);
            viewPager.setCurrentItem(HomeActivity.imageCur);
        } else if (code == GallerieFragment.FRAGMENT_CODE) {
            ImageViewPagerAdapter imageViewPagerAdapter = new ImageViewPagerAdapter(getActivity().getSupportFragmentManager(), HomeActivity.phoTos,code);
            viewPager.setAdapter(imageViewPagerAdapter);
            viewPager.setCurrentItem(HomeActivity.imageCur);
        }


        return view;
    }
}
