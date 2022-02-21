package com.mjafarshidik.moviecatalogue.di

import android.content.Context
import com.mjafarshidik.moviecatalogue.data.source.MovieCatalogueRepository
import com.mjafarshidik.moviecatalogue.data.source.local.LocalDataSource
import com.mjafarshidik.moviecatalogue.data.source.local.room.DatabaseDAO
import com.mjafarshidik.moviecatalogue.data.source.remote.RemoteDataSource
import com.mjafarshidik.moviecatalogue.network.ApiConfig
import com.mjafarshidik.moviecatalogue.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): MovieCatalogueRepository {

        val database = DatabaseDAO.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.getApiService())

        val localDataSource = LocalDataSource.getInstance(database.dataDao())

        val appExecutors = AppExecutors()

        return MovieCatalogueRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}