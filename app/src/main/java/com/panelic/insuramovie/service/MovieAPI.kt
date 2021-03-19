package com.panelic.insuramovie.service

import com.panelic.insuramovie.model.DiscoverResult
import com.panelic.insuramovie.model.GenresResult
import com.panelic.insuramovie.model.PreviewYoutubeResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {
    @GET("genre/movie/list")
    fun getGenres(@Query("api_key") api_key:String, @Query("language") language:String) : Call<GenresResult>

    @GET("discover/movie")
    fun getDiscoverByGenres(@Query("api_key") api_key:String, @Query("with_genres") with_genres:String) : Call<DiscoverResult>

    @GET("movie/{movie_id}/videos")
    fun getVideoPreview(@Path("movie_id") movie_id:String, @Query("api_key") api_key:String) : Call<PreviewYoutubeResult>
}