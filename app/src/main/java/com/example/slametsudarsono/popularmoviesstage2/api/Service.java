package com.example.slametsudarsono.popularmoviesstage2.api;

import com.example.slametsudarsono.popularmoviesstage2.model.MoviesResponse;
import com.example.slametsudarsono.popularmoviesstage2.model.ReviewResponse;
import com.example.slametsudarsono.popularmoviesstage2.model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}/videos")
    Call<TrailerResponse> getMovieTrailer(@Path("movie_id") int id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewResponse> getReview(@Path("movie_id") int id, @Query("api_key") String apiKey);
}
