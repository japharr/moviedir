package com.jeliliadesina.moviedir.movie.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.jeliliadesina.moviedir.data.resultLiveData
import kotlinx.coroutines.CoroutineScope

class MovieRepository(private val dao: MovieDao, private val movieRemoteDataSource: MovieRemoteDataSource) {

    fun observePagedSets(connectivityAvailable: Boolean, coroutineScope: CoroutineScope): LiveData<PagedList<Movie>> {
        println("observePagedSets")
        return if (connectivityAvailable)
            observeRemotePagedMovies(coroutineScope)
        else observeLocalPagedMovies()
    }

    private fun observeLocalPagedMovies(): LiveData<PagedList<Movie>> {
        println("observeLocalPagedMovies")
        val dataSourceFactory = dao.loadPagedAll()

        return LivePagedListBuilder(dataSourceFactory,
            MoviePageDataSourceFactory.pagedListConfig()).build()
    }

    private fun observeRemotePagedMovies(ioCoroutineScope: CoroutineScope)
            : LiveData<PagedList<Movie>> {
        println("observeRemotePagedMovies")
        val dataSourceFactory = MoviePageDataSourceFactory(movieRemoteDataSource, dao, ioCoroutineScope)

        println("observeRemotePagedMovies: $dataSourceFactory")
        return LivePagedListBuilder(dataSourceFactory, MoviePageDataSourceFactory.pagedListConfig()).build()
    }

    fun observeMovie(id: Int) = resultLiveData(
        databaseQuery = { dao.getMovie(id) },
        networkCall = { movieRemoteDataSource.fetchMovie(id) },
        saveCallResult = { dao.insert(it) })
        .distinctUntilChanged()

    fun observeMovies() = resultLiveData(
        databaseQuery = { dao.getMovies() },
        networkCall = { movieRemoteDataSource.fetchMovies(1) },
        saveCallResult = { dao.insertAll(it.results) })

    companion object {

        const val PAGE_SIZE = 100

        // For Singleton instantiation
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(dao: MovieDao, legoSetRemoteDataSource: MovieRemoteDataSource) =
            instance ?: synchronized(this) {
                instance
                    ?: MovieRepository(dao, legoSetRemoteDataSource).also { instance = it }
            }
    }
}