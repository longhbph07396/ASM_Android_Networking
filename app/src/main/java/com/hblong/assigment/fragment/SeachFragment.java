package com.hblong.assigment.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hblong.assigment.R;
import com.hblong.assigment.adapter.ImageAdapter;
import com.hblong.assigment.model.GetFavourite;
import com.hblong.assigment.model.ImageFavourite;
import com.hblong.assigment.service.FlickService;
import com.hblong.assigment.utils.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SeachFragment extends Fragment {
    private EditText edtSeach;
    private ImageButton button;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String FULL_EXTRAS = "views, media, path_alias, url_sq, url_t, url_s, url_q, url_m, url_n, url_z, url_c, url_l, url_o";
    private static final String KEY_TOKEN = "e8ec0403444db0ad6be941eab1962c79";
    private static final String GET_FAVO = "flickr.photos.search";
    private ImageAdapter imageAdapter;
    private List<ImageFavourite> imageFavourites;
    private int pagee;

    private StaggeredGridLayoutManager manager;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_seach, container, false);
        initView(view);
        getData(false);
        return view;


    }

    private void getData(boolean clearData) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.flickr.com").addConverterFactory(GsonConverterFactory.create()).build();
        FlickService flickrService = retrofit.create(FlickService.class);
        flickrService.getTag(FULL_EXTRAS, "1", edtSeach.getText().toString().isEmpty() ? "car" : edtSeach.getText().toString(), "json", KEY_TOKEN, GET_FAVO, 1, 6).enqueue(new Callback<GetFavourite>() {
            @Override
            public void onResponse(Call<GetFavourite> call, Response<GetFavourite> response) {
                Log.e("longhb", response.body().photos.photo.size() + "");
                if (clearData) {
                    imageFavourites.clear();
                }
                imageFavourites.addAll(response.body().photos.photo);
                imageAdapter.notifyDataSetChanged();
                if (clearData) {
                    swipeRefreshLayout.setRefreshing(false);
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
        edtSeach = view.findViewById(R.id.edt_seach);
        button = view.findViewById(R.id.button);
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        progressBar = view.findViewById(R.id.progressBar);


        imageAdapter = new ImageAdapter(getContext(), imageFavourites, FavouriteFragment.FRAGMENT_CODE);
        recyclerView.setAdapter(imageAdapter);
        manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(true);
                pagee=1;
            }
        });

        addScrollListener();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData(true);
                pagee=1;
            }
        });

    }

    private void addScrollListener() {
        EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                progressBar.setVisibility(View.VISIBLE);
                if (pagee==1){
                    page=2;
                    pagee=page;
                }else {
                    page++;
                    pagee=page;
                }
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.flickr.com").addConverterFactory(GsonConverterFactory.create()).build();
                FlickService flickrService = retrofit.create(FlickService.class);
                flickrService.getTag(FULL_EXTRAS, "1", edtSeach.getText().toString().isEmpty() ? "car" : edtSeach.getText().toString(), "json", KEY_TOKEN, GET_FAVO, 2 + pagee, 3).enqueue(new Callback<GetFavourite>() {
                    @Override
                    public void onResponse(Call<GetFavourite> call, Response<GetFavourite> response) {
                        Log.e("longhb", response.body().photos.photo.size() + "");
                        imageFavourites.addAll(response.body().photos.photo);
                        imageAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<GetFavourite> call, Throwable t) {
                        Log.e("longhb", t.getMessage());
                    }
                });
            }
        };
        recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener);
    }
}
