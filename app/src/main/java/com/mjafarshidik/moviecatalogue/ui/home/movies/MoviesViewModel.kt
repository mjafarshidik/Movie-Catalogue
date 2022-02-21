package com.mjafarshidik.moviecatalogue.ui.home.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.mjafarshidik.moviecatalogue.data.source.MovieCatalogueRepository
import com.mjafarshidik.moviecatalogue.data.source.local.entity.MoviesEntity
import com.mjafarshidik.moviecatalogue.vo.Resource

class MoviesViewModel(private val dataRepository: MovieCatalogueRepository) : ViewModel() {

    fun getMovies(): LiveData<Resource<PagedList<MoviesEntity>>> = dataRepository.getMovies()
}