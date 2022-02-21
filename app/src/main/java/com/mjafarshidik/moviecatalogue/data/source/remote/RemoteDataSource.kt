package com.mjafarshidik.moviecatalogue.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mjafarshidik.moviecatalogue.data.source.remote.response.ResultsItem
import com.mjafarshidik.moviecatalogue.data.source.remote.response.ResultsItemTV
import com.mjafarshidik.moviecatalogue.network.ApiService
import com.mjafarshidik.moviecatalogue.utils.EspressoIdlingResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.await
import java.io.IOException

class RemoteDataSource private constructor(private val apiService: ApiService) {
    fun getMovies(): LiveData<ApiResponse<List<ResultsItem>>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<List<ResultsItem>>>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getMovie().await()
                resultMovie.postValue(ApiResponse.success(response.results))
            } catch (e: IOException) {
                e.printStackTrace()
                resultMovie.postValue(
                    ApiResponse.error(
                        e.message.toString(),
                        mutableListOf()
                    )
                )
            }
        }
        EspressoIdlingResource.decrement()
        return resultMovie
    }

    fun getDetailMovies(movieId: Int): LiveData<ApiResponse<ResultsItem>> {
        EspressoIdlingResource.increment()
        val resultDetailMovie = MutableLiveData<ApiResponse<ResultsItem>>()
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiService.getMovieDetail(movieId).await()
            resultDetailMovie.postValue(ApiResponse.success(response))
        }
        EspressoIdlingResource.decrement()
        return resultDetailMovie
    }

    fun getTvShows(): LiveData<ApiResponse<List<ResultsItemTV>>> {
        EspressoIdlingResource.increment()
        val resultTV = MutableLiveData<ApiResponse<List<ResultsItemTV>>>()
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiService.getTV().await()
            resultTV.postValue(ApiResponse.success(response.resultsTV))
        }
        EspressoIdlingResource.decrement()
        return resultTV
    }

    fun getDetailTvShow(tvShowId: Int): LiveData<ApiResponse<ResultsItemTV>> {
        EspressoIdlingResource.increment()
        val resultDetailTV = MutableLiveData<ApiResponse<ResultsItemTV>>()
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiService.getTVDetail(tvShowId).await()
            resultDetailTV.postValue(ApiResponse.success(response))
        }
        EspressoIdlingResource.decrement()
        return resultDetailTV
    }

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service).apply { instance = this }
            }
    }
}