package com.mjafarshidik.moviecatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mjafarshidik.moviecatalogue.data.source.MovieCatalogueRepository
import com.mjafarshidik.moviecatalogue.data.source.local.entity.MoviesEntity
import com.mjafarshidik.moviecatalogue.data.source.local.entity.TVShowsEntity
import com.mjafarshidik.moviecatalogue.vo.Resource

class DetailViewModel(private val dataRepository: MovieCatalogueRepository) : ViewModel() {

    fun getDetailMovies(id: Int): LiveData<Resource<MoviesEntity>> =
        dataRepository.getDetailMovies(id)

    fun getDetailTvShow(id: Int): LiveData<Resource<TVShowsEntity>> =
        dataRepository.getDetailTvShow(id)

    fun setFavoriteMovies(movie: MoviesEntity, state: Boolean) =
        dataRepository.setFavoriteMovies(movie, state)

    fun setFavoriteTVShows(tv: TVShowsEntity, state: Boolean) =
        dataRepository.setFavoriteTVShow(tv, state)
}