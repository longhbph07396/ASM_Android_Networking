package com.hblong.assigment.service;

import com.hblong.assigment.model.GetCategory;
import com.hblong.assigment.model.GetFavourite;
import com.hblong.assigment.model.GetListImageCallerie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickService {
    @GET("services/rest/")
    Call<GetFavourite> getListFavo(@Query("extras") String extras,
                                   @Query("nojsoncallback") String nojsoncallback,
                                   @Query("user_id") String user_id,
                                   @Query("format") String format,
                                   @Query("api_key") String api_key,
                                   @Query("method") String method,
                                   @Query("page") int page,
                                   @Query("per_page") int per_page);

    @GET("services/rest/")
    Call<GetCategory> getListCategory(@Query("primary_photo_extras") String primary_photo_extras,
                                      @Query("nojsoncallback") String nojsoncallback,
                                      @Query("user_id") String user_id,
                                      @Query("format") String format,
                                      @Query("api_key") String api_key,
                                      @Query("method") String method);

    @GET("services/rest/")
    Call<GetListImageCallerie> getListPhotoByCategory(@Query("gallery_id") String gallery_id,
                                                      @Query("nojsoncallback") String nojsoncallback,
                                                      @Query("extras") String extras,
                                                      @Query("format") String format,
                                                      @Query("api_key") String api_key,
                                                      @Query("method") String method,
                                                      @Query("page") int page,
                                                      @Query("per_page") int per_page);

    @GET("services/rest/")
    Call<GetFavourite> getTag(@Query("extras") String extras,
                                   @Query("nojsoncallback") String nojsoncallback,
                                   @Query("tags") String tags,
                                   @Query("format") String format,
                                   @Query("api_key") String api_key,
                                   @Query("method") String method,
                                   @Query("page") int page,
                                   @Query("per_page") int per_page);


}
