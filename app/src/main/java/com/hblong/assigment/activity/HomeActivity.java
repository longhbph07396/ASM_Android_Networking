package com.hblong.assigment.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.hblong.assigment.R;
import com.hblong.assigment.fragment.CategoryFragment;
import com.hblong.assigment.fragment.FavouriteFragment;
import com.hblong.assigment.model.GetListImageCallerie;
import com.hblong.assigment.model.ImageFavourite;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    public static List<ImageFavourite> imageFavourites;
    public static List<GetListImageCallerie.Photos.PhoTo> phoTos;
    public static int imageCur;

    private BottomNavigationView bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        bottomNavigation =  findViewById(R.id.bottom_navigation);



        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new FavouriteFragment()).commit();

        bottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.favourite:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new FavouriteFragment()).commit();
                break;
            case R.id.category:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new CategoryFragment()).commit();
                break;
        }
        return false;
    }
}