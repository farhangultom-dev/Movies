package com.farhandev.movieapp.service.network

import com.farhandev.movieapp.service.network.response.*
import com.farhandev.movieapp.util.NetworkConfig.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing?api_key=$API_KEY")
    fun getMovieNowPlaying(): Call<NowPlayingResponse>

    @GET("movie/popular?api_key=$API_KEY")
    fun getPopularMovie(): Call<PopularResponse>

    @GET("movie/top_rated?api_key=$API_KEY")
    fun getTopRatedovie(): Call<TopRatedResponse>

    @GET("movie/upcoming?api_key=$API_KEY")
    fun getUpcomingMovie(): Call<UpComingResponse>

    @GET("search/movie")
    fun getSearchMovie(
        @Query("query") query: String,
        @Query("api_key") apiKey: String
    ): Call<SarchMovieResponse>

    @GET("genre/movie/list?api_key=$API_KEY")
    fun getGenreMovie(): Call<GenreResponse>

    @GET("movie/{movie_id}/videos")
    fun getVideoMovie(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String
    ): Call<VideoResponse>

    @GET("movie/{movie_id}/reviews")
    fun getReviews(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String
    ): Call<ReviewResponse>

    @GET("discover/movie")
    fun getMoviesByGenre(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genreId: String
    ): Call<SarchMovieResponse>
}