package com.mjafarshidik.moviecatalogue.ui.home.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.mjafarshidik.moviecatalogue.data.source.MovieCatalogueRepository
import com.mjafarshidik.moviecatalogue.data.source.local.entity.TVShowsEntity
import com.mjafarshidik.moviecatalogue.vo.Resource

class TVShowsViewModel(private val dataRepository: MovieCatalogueRepository) : ViewModel() {

    fun getTv(): LiveData<Resource<PagedList<TVShowsEntity>>> = dataRepository.getTvShows()
}