package com.jeliliadesina.moviedir.movie.data

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import androidx.paging.DataSource
import androidx.paging.PagedList

class MoviePageDataSourceFactory constructor(
    private val dataSource: MovieRemoteDataSource,
    private val dao: MovieDao,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Movie>() {
    private val liveData = MutableLiveData<MoviePageDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val source = MoviePageDataSource(dataSource, dao, scope)
        liveData.postValue(source)
        return source
    }

    companion object {
        private const val PAGE_SIZE = 100

        fun pagedListConfig() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
    }
}