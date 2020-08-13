package com.hblong.assigment.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.hblong.assigment.R;
import com.hblong.assigment.acsyntack.DownloadImage;
import com.hblong.assigment.acsyntack.SetWallpaper;
import com.hblong.assigment.adapter.ImageViewPagerAdapter;
import com.hblong.assigment.fragment.FavouriteFragment;
import com.hblong.assigment.fragment.GallerieFragment;
import com.hblong.assigment.model.GetListImageCallerie;
import com.hblong.assigment.model.ImageFavourite;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private ViewPager viewPager2;
    private ImageButton imageButton;

    private int code;
    public List<ImageFavourite> imageFavourites = new ArrayList<>();
    public List<GetListImageCallerie.Photos.PhoTo> phoTos = new ArrayList<>();
    public int imageCur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        viewPager2 = (ViewPager) findViewById(R.id.viewPager2);
        imageButton = findViewById(R.id.imageButton);
        if (getIntent().getSerializableExtra("photo") != null) {
            phoTos = (List<GetListImageCallerie.Photos.PhoTo>) getIntent().getSerializableExtra("photo");
        }
        if (getIntent().getSerializableExtra("images") != null) {
            imageFavourites = (List<ImageFavourite>) getIntent().getSerializableExtra("images");
        }
        code = getIntent().getIntExtra("code", -1);
        imageCur = getIntent().getIntExtra("cur", -1);
        Log.e("longhb", imageCur + " " + code + " " + phoTos.size() + " " + imageFavourites.size());
        if (code == FavouriteFragment.FRAGMENT_CODE) {
            ImageViewPagerAdapter imageViewPagerAdapter = new ImageViewPagerAdapter(getSupportFragmentManager(), imageFavourites);
            viewPager2.setAdapter(imageViewPagerAdapter);
            viewPager2.setCurrentItem(imageCur);
        } else if (code == GallerieFragment.FRAGMENT_CODE) {
            ImageViewPagerAdapter imageViewPagerAdapter = new ImageViewPagerAdapter(getSupportFragmentManager(), phoTos, code);
            viewPager2.setAdapter(imageViewPagerAdapter);
            viewPager2.setCurrentItem(imageCur);
        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu();
            }
        });
    }
    private void showMenu() {

        PopupMenu popupMenu = new PopupMenu(this, imageButton);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                String link = null;
                if (code == FavouriteFragment.FRAGMENT_CODE) {
                    link = linkEmpty(imageFavourites.get(viewPager2.getCurrentItem()).getLink());
                } else {
                    link = linkEmpty(phoTos.get(viewPager2.getCurrentItem()).getLink());
                }
                switch (menuItem.getItemId()) {
                    case R.id.item_download:
                        DownloadImage downloadImage = new DownloadImage(DetailActivity.this);
                        downloadImage.execute(link);
                        break;
                    case R.id.item_set_wallpaper:
                        SetWallpaper setWallpaper = new SetWallpaper(DetailActivity.this);
                        setWallpaper.execute(link);
                        break;
                    case R.id.item_share:
                        shareTextUrl(link);
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_left, R.anim.out_right);
        finish();
    }

    private String linkEmpty(String[] strings) {
        for (int i = strings.length - 1; i >= 0; i--) {
            if (strings[i] != null) {
                return strings[i];
            }
        }
        return null;
    }

    private void shareTextUrl(String url) {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Image Link");
        share.putExtra(Intent.EXTRA_TEXT, url);

        startActivity(Intent.createChooser(share, "Share Image"));
    }
}