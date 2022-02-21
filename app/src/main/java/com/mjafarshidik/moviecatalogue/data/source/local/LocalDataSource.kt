package com.mjafarshidik.moviecatalogue.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.mjafarshidik.moviecatalogue.data.source.local.entity.MoviesEntity
import com.mjafarshidik.moviecatalogue.data.source.local.entity.TVShowsEntity
import com.mjafarshidik.moviecatalogue.data.source.local.room.DataDAO

class LocalDataSource private constructor(private val mDataDao: DataDAO) {
    fun getMovies(): DataSource.Factory<Int, MoviesEntity> = mDataDao.getMovies()

    fun getListFavoriteMovies(): DataSource.Factory<Int, MoviesEntity> =
        mDataDao.getListFavoriteMovies()

    fun getMoviesDetail(movieId: Int): LiveData<MoviesEntity> = mDataDao.getDetailMovie(movieId)

    fun insertMovies(movies: List<MoviesEntity>) = mDataDao.insertMovies(movies)

    fun insertMoviesDetail(movie: MoviesEntity) = mDataDao.insertMovieDetail(movie)

    fun setFavoriteMovies(movie: MoviesEntity, state: Boolean) {
        movie.isFavorite = !state
        mDataDao.updateMovies(movie)
    }

    fun getTVShows(): DataSource.Factory<Int, TVShowsEntity> = mDataDao.getTVShows()

    fun getListFavoriteTVShows(): DataSource.Factory<Int, TVShowsEntity> =
        mDataDao.getListFavoriteTVShows()

    fun getTVShowsDetail(tvId: Int): LiveData<TVShowsEntity> = mDataDao.getDetailTVShows(tvId)

    fun insertTVShows(tvs: List<TVShowsEntity>) = mDataDao.insertTVShows(tvs)

    fun insertTVShowsDetail(tv: TVShowsEntity) = mDataDao.insertTVDetail(tv)

    fun setFavoriteTVShows(tv: TVShowsEntity, state: Boolean) {
        tv.isFavorite = !state
        mDataDao.updateTVShows(tv)
    }

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(dataDao: DataDAO): LocalDataSource =
            INSTANCE ?: LocalDataSource(dataDao)
    }
}