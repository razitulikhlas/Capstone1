package com.razit.core.data.source.remote.network

import com.razit.core.BuildConfig
import com.razit.core.data.source.remote.response.ResponseMovies
import com.razit.core.data.source.remote.response.ResponseSearchMovies
import com.razit.core.data.source.remote.response.ResponseTvShow
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getMoviesPage(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): ResponseMovies

    @GET("tv/popular")
    suspend fun getTvShowPage(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): ResponseTvShow

    @GET("search/movie?")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("query") query: String,
    ): ResponseSearchMovies
}