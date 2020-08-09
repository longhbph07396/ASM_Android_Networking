package com.hblong.assigment.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hblong.assigment.R;
import com.hblong.assigment.activity.HomeActivity;
import com.hblong.assigment.adapter.ImageAdapter;
import com.hblong.assigment.model.ImageFavourite;
import com.hblong.assigment.service.FlickService;
import com.hblong.assigment.model.GetFavourite;
import com.hblong.assigment.utils.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavouriteFragment extends Fragment {
    public static final int FRAGMENT_CODE = 0;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String FULL_EXTRAS = "views,url_z,url_l";
    private static final String USER_ID = "189494349@N08";
    private static final String KEY_TOKEN = "e8ec0403444db0ad6be941eab1962c79";
    private static final String GET_FAVO = "flickr.favorites.getList";
    private int page = 1;
    private int perpage = 100;
    private ImageAdapter imageAdapter;
    private List<ImageFavourite> imageFavourites;
    private List<ImageFavourite> imageFavouriteList;

    private StaggeredGridLayoutManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_favourite, container, false);
        initView(view);
        getData(perpage);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            for (int i = 0; i < 5; i++) {
                imageFavourites.add(imageFavouriteList.get(imageFavourites.size() + i));
                imageAdapter.notifyDataSetChanged();
            }
            swipeRefreshLayout.setRefreshing(false);
        });
        return view;
    }

    private void getData(int a) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.flickr.com").addConverterFactory(GsonConverterFactory.create()).build();
        FlickService flickrService = retrofit.create(FlickService.class);
        flickrService.getListFavo(FULL_EXTRAS, "1", USER_ID, "json", KEY_TOKEN, GET_FAVO, page, a).enqueue(new Callback<GetFavourite>() {
            @Override
            public void onResponse(Call<GetFavourite> call, Response<GetFavourite> response) {
                imageFavouriteList = response.body().photos.photo;
                for (int i = 0; i < 10; i++) {
                    imageFavourites.add(imageFavouriteList.get(i));
                    imageAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<GetFavourite> call, Throwable t) {
                Log.e("longhb", t.getMessage());
            }
        });
    }

    private void initView(View view) {
        imageFavourites = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rcv_images);
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);


        imageAdapter = new ImageAdapter(getContext(), imageFavourites, FRAGMENT_CODE);
        recyclerView.setAdapter(imageAdapter);
        manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);



    }
}
