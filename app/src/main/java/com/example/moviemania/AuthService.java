package com.example.moviemania;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface AuthService {
    @POST("popular")
    Call<MoviesModel> movies(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") String page
    );

    @GET("{id}")
    Call<MovieDetailsModel> Details(
            @Path(value="id") String id,
            @Query("api_key") String api_key
    );
}
