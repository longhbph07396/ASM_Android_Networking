package com.hblong.assigment.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hblong.assigment.R;
import com.hblong.assigment.activity.HomeActivity;
import com.hblong.assigment.adapter.ImageAdapter;
import com.hblong.assigment.model.GetFavourite;
import com.hblong.assigment.model.GetListImageCallerie;
import com.hblong.assigment.model.ImageFavourite;
import com.hblong.assigment.service.FlickService;
import com.hblong.assigment.utils.EndlessRecyclerViewScrollListener;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GallerieFragment extends Fragment {
    public static final int FRAGMENT_CODE = 1;
    private SwipeRefreshLayout swiperefresh;
    private RecyclerView rcvImages;
    private ProgressBar progressBar;
    private List<GetListImageCallerie.Photos.PhoTo> phoTos = new ArrayList<>();
    private StaggeredGridLayoutManager manager;

    private String GALLERY_ID;
    private static final String KEY_TOKEN = "e8ec0403444db0ad6be941eab1962c79";
    private static final String GET_FAVO = "flickr.galleries.getPhotos";
    private static final String FULL_EXTRAS = "views, media, path_alias, url_sq, url_t, url_s, url_q, url_m, url_n, url_z, url_c, url_l, url_o";
    private ImageAdapter imageAdapter;

    public GallerieFragment(String GALLERY_ID) {
        this.GALLERY_ID = GALLERY_ID;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_favourite, container, false);
        initView(view);
        Log.e("longhb", GALLERY_ID);
        getData(false);
        return view;
    }

    private void getData(boolean clear) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.flickr.com").addConverterFactory(GsonConverterFactory.create()).build();
        FlickService flickrService = retrofit.create(FlickService.class);
        flickrService.getListPhotoByCategory(GALLERY_ID, "1", FULL_EXTRAS, "json", KEY_TOKEN, GET_FAVO, 1, 6).enqueue(new Callback<GetListImageCallerie>() {
            @Override
            public void onResponse(Call<GetListImageCallerie> call, Response<GetListImageCallerie> response) {
                if (clear) {
                    phoTos.clear();
                }
                phoTos.addAll(response.body().photos.photo);
                imageAdapter.notifyDataSetChanged();
                if (clear) {
                    swiperefresh.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<GetListImageCallerie> call, Throwable t) {

            }
        });
    }

    private void initView(View view) {
        swiperefresh = view.findViewById(R.id.swiperefresh);
        rcvImages = view.findViewById(R.id.rcv_images);
        progressBar = view.findViewById(R.id.progressBar);
        manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        imageAdapter = new ImageAdapter(FRAGMENT_CODE, getContext(), phoTos);
        rcvImages.setAdapter(imageAdapter);
        rcvImages.setLayoutManager(manager);
        addScrollListener();

        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(true);
            }
        });
    }

    private void addScrollListener() {
        EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                progressBar.setVisibility(View.VISIBLE);
                page++;
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.flickr.com").addConverterFactory(GsonConverterFactory.create()).build();
                FlickService flickrService = retrofit.create(FlickService.class);
                flickrService.getListPhotoByCategory(GALLERY_ID, "1", FULL_EXTRAS, "json", KEY_TOKEN, GET_FAVO, 2+page, 3).enqueue(new Callback<GetListImageCallerie>() {
                    @Override
                    public void onResponse(Call<GetListImageCallerie> call, Response<GetListImageCallerie> response) {
                        phoTos.addAll(response.body().photos.photo);
                        imageAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<GetListImageCallerie> call, Throwable t) {

                    }
                });
            }
        };
        rcvImages.addOnScrollListener(endlessRecyclerViewScrollListener);
    }
}
