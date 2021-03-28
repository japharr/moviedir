package com.jeliliadesina.moviedir.movie.ui

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.jeliliadesina.moviedir.movie.data.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

class MoviesViewModel constructor(
    private val repository: MovieRepository, private val ioCoroutineScope: CoroutineScope
) : ViewModel()  {
    var connectivityAvailable: ObservableField<Boolean> = ObservableField(false)

    val movies by lazy {
        repository.observePagedSets(connectivityAvailable.get() ?: false, ioCoroutineScope)
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        ioCoroutineScope.cancel()
    }
}