package com.jeliliadesina.moviedir.movie.ui

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.jeliliadesina.moviedir.movie.data.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MovieRepository, private val ioCoroutineScope: CoroutineScope
) : ViewModel()  {
    var connectivityAvailable: ObservableField<Boolean> = ObservableField(true)

    val movies by lazy {
        repository.observePagedSets(connectivityAvailable.get() ?: true, ioCoroutineScope)
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        ioCoroutineScope.cancel()
    }
}