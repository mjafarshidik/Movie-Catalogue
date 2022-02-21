package com.mjafarshidik.moviecatalogue.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.mjafarshidik.moviecatalogue.data.source.local.entity.MoviesEntity
import com.mjafarshidik.moviecatalogue.data.source.local.entity.TVShowsEntity

@Dao
interface DataDAO {
    @Query("SELECT * FROM movies_entity")
    fun getMovies(): DataSource.Factory<Int, MoviesEntity>

    @Query("SELECT * FROM movies_entity WHERE isFavorite = 1")
    fun getListFavoriteMovies(): DataSource.Factory<Int, MoviesEntity>

    @Query("SELECT * FROM movies_entity WHERE id = :movieId")
    fun getDetailMovie(movieId: Int): LiveData<MoviesEntity>

    @Query("SELECT * FROM tv_shows_entity")
    fun getTVShows(): DataSource.Factory<Int, TVShowsEntity>

    @Query("SELECT * FROM tv_shows_entity WHERE isFavorite = 1")
    fun getListFavoriteTVShows(): DataSource.Factory<Int, TVShowsEntity>

    @Query("SELECT * FROM tv_shows_entity WHERE id = :tvId")
    fun getDetailTVShows(tvId: Int): LiveData<TVShowsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = MoviesEntity::class)
    fun insertMovies(movies: List<MoviesEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = MoviesEntity::class)
    fun insertMovieDetail(movie: MoviesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = TVShowsEntity::class)
    fun insertTVShows(tv: List<TVShowsEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = TVShowsEntity::class)
    fun insertTVDetail(tv: TVShowsEntity)

    @Update(entity = MoviesEntity::class)
    fun updateMovies(movie: MoviesEntity)

    @Update(entity = TVShowsEntity::class)
    fun updateTVShows(tv: TVShowsEntity)
}