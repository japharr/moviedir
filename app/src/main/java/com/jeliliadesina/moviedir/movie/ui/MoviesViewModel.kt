package com.jeliliadesina.moviedir.movie.ui

import com.jeliliadesina.moviedir.movie.data.MovieRepository
import com.jeliliadesina.moviedir.util.ui.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

class MoviesViewModel constructor(
    private val repository: MovieRepository, private val ioCoroutineScope: CoroutineScope
) : BaseViewModel<MoviesNavigator>()  {
    var connectivityAvailable: Boolean = false

    val movies by lazy {
        repository.observePagedSets(connectivityAvailable, ioCoroutineScope)
    }

    //var movies: () -> LiveData<PagedList<Movie>> = { repository.observePagedSets(connectivityAvailable, ioCoroutineScope) }

    //var movies: LiveData<PagedList<Movie>> = MutableLiveData()

    init {
        //repository.observePagedSets(connectivityAvailable, ioCoroutineScope)
        //load()
    }

    fun load() {
        println("loading...")
        //movies = repository.observePagedSets(connectivityAvailable, ioCoroutineScope)
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        ioCoroutineScope.cancel()
    }
}