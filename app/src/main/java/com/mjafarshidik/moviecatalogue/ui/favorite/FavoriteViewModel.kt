package com.mjafarshidik.moviecatalogue.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.mjafarshidik.moviecatalogue.data.source.MovieCatalogueRepository
import com.mjafarshidik.moviecatalogue.data.source.local.entity.MoviesEntity
import com.mjafarshidik.moviecatalogue.data.source.local.entity.TVShowsEntity

class FavoriteViewModel(private val dataRepository: MovieCatalogueRepository) : ViewModel()  {

    fun getFavoriteMovies(): LiveData<PagedList<MoviesEntity>> = dataRepository.getListFavoriteMovies()

    fun getFavoriteTVShow(): LiveData<PagedList<TVShowsEntity>> = dataRepository.getListFavoriteTVShow()

    fun setFavoriteMovies(movieEntity: MoviesEntity, newState: Boolean) {
        dataRepository.setFavoriteMovies(movieEntity, newState)
    }

    fun setFavoriteTVShow(tvEntity: TVShowsEntity, newState: Boolean) {
        dataRepository.setFavoriteTVShow(tvEntity, newState)
    }
}