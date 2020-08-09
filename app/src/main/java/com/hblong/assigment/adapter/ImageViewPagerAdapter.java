package com.hblong.assigment.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hblong.assigment.fragment.ImageDetailFragment1;
import com.hblong.assigment.model.GetListImageCallerie;
import com.hblong.assigment.model.ImageFavourite;

import java.util.ArrayList;
import java.util.List;

public class ImageViewPagerAdapter extends FragmentPagerAdapter {
    private List<ImageFavourite> imageFavourites=new ArrayList<>();
    private List<GetListImageCallerie.Photos.PhoTo> phoTos=new ArrayList<>();
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
            return new ImageDetailFragment1(imageFavourites.get(position).url_l);
        } else {
            return new ImageDetailFragment1(phoTos.get(position).url_l);
        }
    }

    @Override
    public int getCount() {
        return code == 0 ? imageFavourites.size() : phoTos.size();
    }
}
