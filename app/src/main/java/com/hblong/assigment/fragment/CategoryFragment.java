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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hblong.assigment.R;
import com.hblong.assigment.adapter.CategoryAdapter;
import com.hblong.assigment.adapter.ImageAdapter;
import com.hblong.assigment.model.GetCategory;
import com.hblong.assigment.model.GetFavourite;
import com.hblong.assigment.service.FlickService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryFragment extends Fragment {

    private static final String PRIMANY_PHOTO_EXTRAS = " license, date_upload, date_taken, owner_name, icon_server, original_format, last_update, geo, tags, machine_tags, o_dims, views, media, path_alias, url_sq, url_t, url_s, url_m, url_o";
    private static final String USER_ID = "189494349@N08";
    private static final String KEY_TOKEN = "e8ec0403444db0ad6be941eab1962c79";
    private static final String GET_CATEGORY_LIST = "flickr.galleries.getList";
    private List<GetCategory.Galleries.Gallery> galleries;
    private CategoryAdapter categoryAdapter;
    private SwipeRefreshLayout swiperefresh;
    private RecyclerView rcvImages;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_category, container, false);
        swiperefresh = view.findViewById(R.id.swiperefresh);
        rcvImages = view.findViewById(R.id.rcv_images);
        progressBar = view.findViewById(R.id.progressBar);

        getData();

        return view;
    }

    private void getData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.flickr.com").addConverterFactory(GsonConverterFactory.create()).build();
        FlickService flickrService = retrofit.create(FlickService.class);
        flickrService.getListCategory(PRIMANY_PHOTO_EXTRAS, "1", USER_ID, "json", KEY_TOKEN, GET_CATEGORY_LIST).enqueue(new Callback<GetCategory>() {
            @Override
            public void onResponse(Call<GetCategory> call, Response<GetCategory> response) {
                galleries = response.body().galleries.gallery;
                categoryAdapter = new CategoryAdapter(getContext(), galleries);
                rcvImages.setAdapter(categoryAdapter);
                rcvImages.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            }

            @Override
            public void onFailure(Call<GetCategory> call, Throwable t) {

            }
        });
    }
}
