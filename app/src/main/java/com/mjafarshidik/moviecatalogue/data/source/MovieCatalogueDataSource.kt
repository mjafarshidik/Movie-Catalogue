package com.mjafarshidik.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.mjafarshidik.moviecatalogue.data.source.local.entity.MoviesEntity
import com.mjafarshidik.moviecatalogue.data.source.local.entity.TVShowsEntity
import com.mjafarshidik.moviecatalogue.vo.Resource

interface MovieCatalogueDataSource {
    fun getMovies(): LiveData<Resource<PagedList<MoviesEntity>>>

    fun getDetailMovies(movieId: Int): LiveData<Resource<MoviesEntity>>

    fun getListFavoriteMovies(): LiveData<PagedList<MoviesEntity>>

    fun setFavoriteMovies(movie: MoviesEntity, state: Boolean)

    fun getTvShows(): LiveData<Resource<PagedList<TVShowsEntity>>>

    fun getDetailTvShow(tvShowId: Int): LiveData<Resource<TVShowsEntity>>

    fun getListFavoriteTVShow(): LiveData<PagedList<TVShowsEntity>>

    fun setFavoriteTVShow(tv: TVShowsEntity, state: Boolean)

}