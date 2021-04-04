package com.jeliliadesina.moviedir.movie.ui

import androidx.lifecycle.ViewModel
import com.jeliliadesina.moviedir.movie.data.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.properties.Delegates


@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel()  {
    var id by Delegates.notNull<Int>()

    val movie by lazy { repository.observeMovie(id) }
}