package com.mjafarshidik.moviecatalogue.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.mjafarshidik.moviecatalogue.data.source.local.LocalDataSource
import com.mjafarshidik.moviecatalogue.data.source.local.entity.MoviesEntity
import com.mjafarshidik.moviecatalogue.data.source.local.entity.TVShowsEntity
import com.mjafarshidik.moviecatalogue.data.source.remote.RemoteDataSource
import com.mjafarshidik.moviecatalogue.utils.AppExecutors
import com.mjafarshidik.moviecatalogue.utils.DataDummy
import com.mjafarshidik.moviecatalogue.utils.LiveDataTestUtil
import com.mjafarshidik.moviecatalogue.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MovieCatalogueRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val dataRepository = FakeDataRepository(remote, local, appExecutors)

    private val movieResponses = DataDummy.getAllMovies()
    private val movieResponsesDetail = DataDummy.getAllMovies()[0]
    private val movieId = movieResponses[0].id
    private val tvShowResponses = DataDummy.getAllTvShows()
    private val tvShowResponsesDetail = DataDummy.getAllTvShows()[0]
    private val tvShowId = tvShowResponses[0].id

    @Test
    fun getMovies() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MoviesEntity>
        `when`(local.getMovies()).thenReturn(dataSourceFactory)
        dataRepository.getMovies()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(movieResponses))
        verify(local).getMovies()
        assertNotNull(movieEntities.data)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getMovieDetail() {
        val dummyMovie = MutableLiveData<MoviesEntity>()
        dummyMovie.value = movieResponsesDetail
        `when`(movieId?.let { local.getMoviesDetail(it) }).thenReturn(dummyMovie)

        val movieEntity = LiveDataTestUtil.getValue(dataRepository.getDetailMovies(movieId!!))
        verify(local).getMoviesDetail(movieId)
        assertNotNull(movieEntity)
        assertEquals(movieResponsesDetail.id, movieEntity.data?.id)

    }

    @Test
    fun getTVShow() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TVShowsEntity>
        `when`(local.getTVShows()).thenReturn(dataSourceFactory)
        dataRepository.getTvShows()

        val tvEntities = Resource.success(PagedListUtil.mockPagedList(tvShowResponses))
        verify(local).getTVShows()
        assertNotNull(tvEntities.data)
        assertEquals(tvShowResponses.size.toLong(), tvEntities.data?.size?.toLong())
    }

    @Test
    fun getTvDetail() {
        val dummyTV = MutableLiveData<TVShowsEntity>()
        dummyTV.value = tvShowResponsesDetail
        `when`(tvShowId?.let { local.getTVShowsDetail(it) }).thenReturn(dummyTV)

        val tvEntity = LiveDataTestUtil.getValue(dataRepository.getDetailTvShow(tvShowId!!))
        verify(local).getTVShowsDetail(tvShowId)
        assertNotNull(tvEntity)
        assertEquals(tvShowResponsesDetail.id, tvEntity.data?.id)
    }

    @Test
    fun getListFavoriteMovies() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MoviesEntity>
        `when`(local.getListFavoriteMovies()).thenReturn(dataSourceFactory)
        dataRepository.getListFavoriteMovies()

        val movieFavEntities = Resource.success(PagedListUtil.mockPagedList(movieResponses))
        verify(local).getListFavoriteMovies()
        assertNotNull(movieFavEntities)
        assertEquals(movieResponses.size.toLong(), movieFavEntities.data?.size?.toLong())
    }

    @Test
    fun getListFavoriteTVShows(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TVShowsEntity>
        `when`(local.getListFavoriteTVShows()).thenReturn(dataSourceFactory)
        dataRepository.getListFavoriteTVShow()

        val tvFavEntities = Resource.success(PagedListUtil.mockPagedList(tvShowResponses))
        verify(local).getListFavoriteTVShows()
        assertNotNull(tvFavEntities)
        assertEquals(tvShowResponses.size.toLong(), tvFavEntities.data?.size?.toLong())
    }
}