package com.jeliliadesina.moviedir.movie.ui

import androidx.lifecycle.ViewModel
import com.jeliliadesina.moviedir.movie.data.MovieRepository
import kotlin.properties.Delegates

class MovieViewModel constructor(private val repository: MovieRepository) : ViewModel()  {
    var id by Delegates.notNull<Int>()

    val movie by lazy { repository.observeMovie(id) }
}