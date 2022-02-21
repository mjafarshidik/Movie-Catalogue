package com.mjafarshidik.moviecatalogue.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.mjafarshidik.moviecatalogue.data.source.MovieCatalogueRepository
import com.mjafarshidik.moviecatalogue.data.source.local.entity.MoviesEntity
import com.mjafarshidik.moviecatalogue.data.source.local.entity.TVShowsEntity
import com.mjafarshidik.moviecatalogue.utils.DataDummy
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executors

@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest {
    private lateinit var viewModel: FavoriteViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dataRepository: MovieCatalogueRepository

    @Mock
    private lateinit var observerMovie: Observer<PagedList<MoviesEntity>>

    @Mock
    private lateinit var observerTV: Observer<PagedList<TVShowsEntity>>

    @Before
    fun setUp(){
        viewModel = FavoriteViewModel(dataRepository)
    }

    @Test
    fun `getFavoriteMovie should be success`() {
        val expected = MutableLiveData<PagedList<MoviesEntity>>()
        expected.value = PagedTestDataSources.snapshot(DataDummy.getAllMovies())
        `when`(dataRepository.getListFavoriteMovies()).thenReturn(expected)

        viewModel.getFavoriteMovies().observeForever(observerMovie)
        verify(observerMovie).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getFavoriteMovies().value
        assertEquals(expectedValue, actualValue)
        assertEquals(expectedValue?.snapshot(), actualValue?.snapshot())
        assertEquals(expectedValue?.size, actualValue?.size)
    }

    @Test
    fun `getFavoriteMovie should be access but data is empty`() {
        val expected = MutableLiveData<PagedList<MoviesEntity>>()
        expected.value = PagedTestDataSources.snapshot()

        `when`(dataRepository.getListFavoriteMovies()).thenReturn(expected)

        viewModel.getFavoriteMovies().observeForever(observerMovie)
        verify(observerMovie).onChanged(expected.value)

        val actualValueDataSize = viewModel.getFavoriteMovies().value?.size
        assertTrue("size of data should be 0, actual is $actualValueDataSize", actualValueDataSize == 0)
    }

    @Test
    fun `getFavoriteTVShows should be success`() {
        val expected = MutableLiveData<PagedList<TVShowsEntity>>()
        expected.value = PagedTestDataSourcesTV.snapshot2(DataDummy.getAllTvShows())

        `when`(dataRepository.getListFavoriteTVShow()).thenReturn(expected)

        viewModel.getFavoriteTVShow().observeForever(observerTV)
        verify(observerTV).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getFavoriteTVShow().value
        assertEquals(expectedValue, actualValue)
        assertEquals(expectedValue?.snapshot(), actualValue?.snapshot())
        assertEquals(expectedValue?.size, actualValue?.size)
    }

    @Test
    fun `getFavoriteTVShows should be access but data is empty`() {
        val expected = MutableLiveData<PagedList<TVShowsEntity>>()
        expected.value = PagedTestDataSourcesTV.snapshot2()

        `when`(dataRepository.getListFavoriteTVShow()).thenReturn(expected)

        viewModel.getFavoriteTVShow().observeForever(observerTV)
        verify(observerTV).onChanged(expected.value)

        val actualValueDataSize = viewModel.getFavoriteTVShow().value?.size
        assertTrue("size of data should be 0, actual is $actualValueDataSize", actualValueDataSize == 0)
    }

    class PagedTestDataSources private constructor(private val items: List<MoviesEntity>) : PositionalDataSource<MoviesEntity>() {
        companion object {
            fun snapshot(items: List<MoviesEntity> = listOf()): PagedList<MoviesEntity> {
                return PagedList.Builder(PagedTestDataSources(items), 10)
                    .setNotifyExecutor(Executors.newSingleThreadExecutor())
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .build()
            }
        }

        override fun loadInitial(
            params: LoadInitialParams,
            callback: LoadInitialCallback<MoviesEntity>
        ) {
            callback.onResult(items, 0, items.size)
        }

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<MoviesEntity>) {
            val start = params.startPosition
            val end = params.startPosition + params.loadSize
            callback.onResult(items.subList(start, end))
        }
    }

    class PagedTestDataSourcesTV private constructor(private val items: List<TVShowsEntity>) : PositionalDataSource<TVShowsEntity>() {
        companion object {
            fun snapshot2(items: List<TVShowsEntity> = listOf()): PagedList<TVShowsEntity> {
                return PagedList.Builder(PagedTestDataSourcesTV(items), 10)
                    .setNotifyExecutor(Executors.newSingleThreadExecutor())
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .build()
            }
        }

        override fun loadInitial(
            params: LoadInitialParams,
            callback: LoadInitialCallback<TVShowsEntity>
        ) {
            callback.onResult(items, 0, items.size)
        }

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<TVShowsEntity>) {
            val start = params.startPosition
            val end = params.startPosition + params.loadSize
            callback.onResult(items.subList(start, end))
        }
    }
}