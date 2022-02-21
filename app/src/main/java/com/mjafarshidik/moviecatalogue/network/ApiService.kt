package com.mjafarshidik.moviecatalogue.network


import com.mjafarshidik.moviecatalogue.data.source.remote.response.ResponseMovie
import com.mjafarshidik.moviecatalogue.data.source.remote.response.ResponseTVShows
import com.mjafarshidik.moviecatalogue.data.source.remote.response.ResultsItem
import com.mjafarshidik.moviecatalogue.data.source.remote.response.ResultsItemTV
import com.mjafarshidik.moviecatalogue.utils.NetworkInfo.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/upcoming")
    fun getMovie(
        @Query("api_key") apiKey: String = API_KEY
    ): Call<ResponseMovie>

    @GET("movie/{id}")
    fun getMovieDetail(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Call<ResultsItem>

    @GET("tv/on_the_air")
    fun getTV(
        @Query("api_key") apiKey: String = API_KEY
    ): Call<ResponseTVShows>

    @GET("tv/{id}")
    fun getTVDetail(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Call<ResultsItemTV>
}