package com.hblong.assigment.fragment;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.chrisbanes.photoview.PhotoView;
import com.hblong.assigment.R;
import com.hblong.assigment.acsyntack.DownloadImage;
import com.hblong.assigment.acsyntack.SetWallpaper;
import com.hblong.assigment.model.ImageFavourite;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageDetailFragment extends Fragment {
    private PhotoView photoView;
    private ProgressBar progressBar;
    private String link;

    public ImageDetailFragment(String link) {
        this.link = link;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_image_1, container, false);
        photoView = view.findViewById(R.id.photoView);
        progressBar = view.findViewById(R.id.progressBar2);
        Glide.with(getContext()).load(link).into(photoView);
        photoView.buildDrawingCache();
        progressBar.setVisibility(View.GONE);


        return view;
    }


}
