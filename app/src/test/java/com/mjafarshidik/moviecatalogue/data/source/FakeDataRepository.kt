package com.mjafarshidik.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mjafarshidik.moviecatalogue.data.source.local.LocalDataSource
import com.mjafarshidik.moviecatalogue.data.source.local.entity.MoviesEntity
import com.mjafarshidik.moviecatalogue.data.source.local.entity.TVShowsEntity
import com.mjafarshidik.moviecatalogue.data.source.remote.ApiResponse
import com.mjafarshidik.moviecatalogue.data.source.remote.RemoteDataSource
import com.mjafarshidik.moviecatalogue.data.source.remote.response.ResultsItem
import com.mjafarshidik.moviecatalogue.data.source.remote.response.ResultsItemTV
import com.mjafarshidik.moviecatalogue.utils.AppExecutors
import com.mjafarshidik.moviecatalogue.vo.Resource

class FakeDataRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): MovieCatalogueDataSource {

    override fun getMovies(): LiveData<Resource<PagedList<MoviesEntity>>> {
        return object : NetworkBoundResource<PagedList<MoviesEntity>, List<ResultsItem>>(appExecutors){
            override fun loadFromDB(): LiveData<PagedList<MoviesEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MoviesEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<ResultsItem>>> =
                remoteDataSource.getMovies()

            public override fun saveCallResult(data: List<ResultsItem>) {
                val movieList = ArrayList<MoviesEntity>()
                for(item in data){
                    val movies = MoviesEntity(
                        null,
                        item.posterPath,
                        item.id,
                        item.title,
                        item.releaseDate,
                        item.voteAverage.toString(),
                        item.overview,
                        false
                    )
                    movieList.add(movies)
                }
                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getDetailMovies(movieId: Int): LiveData<Resource<MoviesEntity>> {
        return object : NetworkBoundResource<MoviesEntity, ResultsItem>(appExecutors){
            override fun loadFromDB(): LiveData<MoviesEntity> =
                localDataSource.getMoviesDetail(movieId)

            override fun shouldFetch(data: MoviesEntity?): Boolean =
                data == null

            public override fun createCall(): LiveData<ApiResponse<ResultsItem>> =
                remoteDataSource.getDetailMovies(movieId)

            override fun saveCallResult(data: ResultsItem) {
                val movie = MoviesEntity(
                    null,
                    data.posterPath,
                    data.id,
                    data.title,
                    data.releaseDate,
                    data.voteAverage.toString(),
                    data.overview,
                    false
                )
                localDataSource.insertMoviesDetail(movie)
            }
        }.asLiveData()
    }

    override fun getTvShows(): LiveData<Resource<PagedList<TVShowsEntity>>> {
        return object : NetworkBoundResource<PagedList<TVShowsEntity>, List<ResultsItemTV>>(appExecutors){
            public override fun loadFromDB(): LiveData<PagedList<TVShowsEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getTVShows(), config).build()
            }

            override fun shouldFetch(data: PagedList<TVShowsEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<ResultsItemTV>>> =
                remoteDataSource.getTvShows()

            public override fun saveCallResult(data: List<ResultsItemTV>) {
                val tvShowList = ArrayList<TVShowsEntity>()
                for(item in data){
                    val tvShow = TVShowsEntity(
                        null,
                        item.posterPath,
                        item.id,
                        item.originalName,
                        item.firstAirDate,
                        item.voteAverage.toString(),
                        item.overview,
                        false
                    )
                    tvShowList.add(tvShow)
                }
                localDataSource.insertTVShows(tvShowList)
            }
        }.asLiveData()
    }

    override fun getDetailTvShow(tvShowId: Int): LiveData<Resource<TVShowsEntity>> {
        return object : NetworkBoundResource<TVShowsEntity, ResultsItemTV>(appExecutors){
            override fun loadFromDB(): LiveData<TVShowsEntity> =
                localDataSource.getTVShowsDetail(tvShowId)

            override fun shouldFetch(data: TVShowsEntity?): Boolean =
                data == null

            public override fun createCall(): LiveData<ApiResponse<ResultsItemTV>> =
                remoteDataSource.getDetailTvShow(tvShowId)

            override fun saveCallResult(data: ResultsItemTV) {
                val tv = TVShowsEntity(
                    null,
                    data.posterPath,
                    data.id,
                    data.originalName,
                    data.firstAirDate,
                    data.voteAverage.toString(),
                    data.overview,
                    false
                )
                localDataSource.insertTVShowsDetail(tv)
            }
        }.asLiveData()
    }

    override fun getListFavoriteMovies(): LiveData<PagedList<MoviesEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getListFavoriteMovies(), config).build()
    }

    override fun setFavoriteMovies(movie: MoviesEntity, state: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovies(movie, state) }

    override fun getListFavoriteTVShow(): LiveData<PagedList<TVShowsEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getListFavoriteTVShows(), config).build()
    }

    override fun setFavoriteTVShow(tv: TVShowsEntity, state: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setFavoriteTVShows(tv, state) }
}