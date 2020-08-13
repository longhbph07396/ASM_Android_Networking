package com.hblong.assigment.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.hblong.assigment.R;
import com.hblong.assigment.fragment.CategoryFragment;
import com.hblong.assigment.fragment.FavouriteFragment;
import com.hblong.assigment.fragment.SeachFragment;
import com.hblong.assigment.model.GetListImageCallerie;
import com.hblong.assigment.model.ImageFavourite;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.e("longhb", "home");

        bottomNavigation = findViewById(R.id.bottom_navigation);


        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new FavouriteFragment()).commit();

        bottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.favourite:
                back(new FavouriteFragment(), R.id.frame_layout);
                break;
            case R.id.category:
                back(new CategoryFragment(), R.id.frame_layout);
                break;
            case R.id.seach:
                back(new SeachFragment(), R.id.frame_layout);
                break;
        }
        return false;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("longhb", "kill");
    }

    public void back(Fragment fragment, int idFrame) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);

        ft.replace(idFrame, fragment);

        ft.commit();
    }
}