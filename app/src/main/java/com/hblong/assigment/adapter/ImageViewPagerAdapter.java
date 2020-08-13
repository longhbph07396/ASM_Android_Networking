package com.hblong.assigment.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hblong.assigment.fragment.ImageDetailFragment;
import com.hblong.assigment.model.GetListImageCallerie;
import com.hblong.assigment.model.ImageFavourite;

import java.util.ArrayList;
import java.util.List;

public class ImageViewPagerAdapter extends FragmentPagerAdapter {
    private List<ImageFavourite> imageFavourites = new ArrayList<>();
    private List<GetListImageCallerie.Photos.PhoTo> phoTos = new ArrayList<>();
    private int code = 0;

    public ImageViewPagerAdapter(@NonNull FragmentManager fm, List<ImageFavourite> imageFavourites) {
        super(fm);
        this.imageFavourites = imageFavourites;
    }

    public ImageViewPagerAdapter(@NonNull FragmentManager supportFragmentManager, List<GetListImageCallerie.Photos.PhoTo> phoTos, int a) {
        super(supportFragmentManager);
        this.phoTos = phoTos;
        this.code = a;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (code == 0) {
            return new ImageDetailFragment(linkEmpty(imageFavourites.get(position).getLink()));
        } else {
            return new ImageDetailFragment(linkEmpty(phoTos.get(position).getLink()));
        }
    }

    @Override
    public int getCount() {
        return code == 0 ? imageFavourites.size() : phoTos.size();
    }

    private String linkEmpty(String[] strings) {
        for (int i = strings.length - 1; i >= 0; i--) {
            if (strings[i] != null) {
                return strings[i];
            }
        }
        return null;
    }
}
