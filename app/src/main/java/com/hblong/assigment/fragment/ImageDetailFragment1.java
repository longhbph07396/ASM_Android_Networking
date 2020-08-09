package com.hblong.assigment.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.hblong.assigment.R;
import com.hblong.assigment.model.ImageFavourite;

public class ImageDetailFragment1 extends Fragment {
    private PhotoView photoView;
    private String link;

    public ImageDetailFragment1(String link) {
        this.link = link;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_image_1, container, false);
        photoView = view.findViewById(R.id.photoView);
        Glide.with(getContext()).load(link).placeholder(R.drawable.bg_splash).into(photoView);
        return view;
    }
}
