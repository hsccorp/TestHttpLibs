package com.aggarwalankur.testhttplibs.retrofit;

import com.aggarwalankur.testhttplibs.MovieResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ankur on 7/12/2016.
 */
public interface RequestInterface {
    @GET("3/movie/top_rated")
    Call<MovieResults> getMovieResults(@Query("api_key") String apiKey);
}
