package com.mjafarshidik.moviecatalogue.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mjafarshidik.moviecatalogue.data.source.MovieCatalogueRepository
import com.mjafarshidik.moviecatalogue.data.source.local.entity.MoviesEntity
import com.mjafarshidik.moviecatalogue.data.source.local.entity.TVShowsEntity
import com.mjafarshidik.moviecatalogue.utils.DataDummy
import com.mjafarshidik.moviecatalogue.vo.Resource
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel

    private val dummyMovie = DataDummy.getAllMovies()[0]
    private val movieId = dummyMovie.id
    private val dummyTVShow = DataDummy.getAllTvShows()[0]
    private val tvId = dummyTVShow.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dataRepository: MovieCatalogueRepository

    @Mock
    private lateinit var observerMovies: Observer<Resource<MoviesEntity>>

    @Mock
    private lateinit var observerTVShow: Observer<Resource<TVShowsEntity>>

    @Before
    fun setUp() {
        viewModel = DetailViewModel(dataRepository)
    }

    @Test
    fun getMovieDetail() {
        val dummyMovie = Resource.success(DataDummy.getAllMovies()[0])
        val movies = MutableLiveData<Resource<MoviesEntity>>()
        movies.value = dummyMovie

        `when`(movieId?.let { dataRepository.getDetailMovies(it) }).thenReturn(movies)
        val movieEntity = movieId?.let { viewModel.getDetailMovies(it).value?.data }

        movieId?.let { verify(dataRepository).getDetailMovies(it) }
        assertNotNull(movieEntity)
        assertEquals(dummyMovie.data?.id, movieEntity?.id)
        assertEquals(dummyMovie.data?.poster, movieEntity?.poster)
        assertEquals(dummyMovie.data?.title, movieEntity?.title)
        assertEquals(dummyMovie.data?.date, movieEntity?.date)
        assertEquals(dummyMovie.data?.score, movieEntity?.score)
        assertEquals(dummyMovie.data?.overview, movieEntity?.overview)

        movieId?.let { viewModel.getDetailMovies(it).observeForever(observerMovies) }
        verify(observerMovies).onChanged(dummyMovie)
    }

    @Test
    fun getTVShowsDetail() {
        val dummyTVShow = Resource.success(DataDummy.getAllTvShows()[0])
        val tvShows = MutableLiveData<Resource<TVShowsEntity>>()
        tvShows.value = dummyTVShow

        `when`(tvId?.let { dataRepository.getDetailTvShow(it) }).thenReturn(tvShows)
        val tvEntity = tvId?.let { viewModel.getDetailTvShow(it).value?.data }

        tvId?.let { verify(dataRepository).getDetailTvShow(it) }
        assertNotNull(tvEntity)
        assertEquals(dummyTVShow.data?.id, tvEntity?.id)
        assertEquals(dummyTVShow.data?.poster, tvEntity?.poster)
        assertEquals(dummyTVShow.data?.title, tvEntity?.title)
        assertEquals(dummyTVShow.data?.date, tvEntity?.date)
        assertEquals(dummyTVShow.data?.score, tvEntity?.score)
        assertEquals(dummyTVShow.data?.overview, tvEntity?.overview)

        tvId?.let { viewModel.getDetailTvShow(it).observeForever(observerTVShow) }
        verify(observerTVShow).onChanged(dummyTVShow)
    }
}