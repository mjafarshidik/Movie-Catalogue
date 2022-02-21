package com.mjafarshidik.moviecatalogue.ui.home.tvshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.mjafarshidik.moviecatalogue.data.source.MovieCatalogueRepository
import com.mjafarshidik.moviecatalogue.data.source.local.entity.TVShowsEntity
import com.mjafarshidik.moviecatalogue.vo.Resource
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TVShowsViewModelTest {

    private lateinit var tvShowsViewModel: TVShowsViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dataRepository: MovieCatalogueRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<TVShowsEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<TVShowsEntity>

    @Before
    fun setUp(){
        tvShowsViewModel = TVShowsViewModel(dataRepository)
    }

    @Test
    fun getTVShows() {
        val dummyTVShow = Resource.success(pagedList)
        `when`(dummyTVShow.data?.size).thenReturn(20)
        val tvShows = MutableLiveData<Resource<PagedList<TVShowsEntity>>>()
        tvShows.value = dummyTVShow

        `when`(dataRepository.getTvShows()).thenReturn(tvShows)

        val tvShowEntities = tvShowsViewModel.getTv().value?.data

        verify(dataRepository).getTvShows()
        assertNotNull(tvShowEntities)
        assertEquals(20, tvShowEntities?.size)

        tvShowsViewModel.getTv().observeForever(observer)
        verify(observer).onChanged(dummyTVShow)
    }
}