package com.jeliliadesina.moviedir.movie.data

import com.jeliliadesina.moviedir.api.BaseDataSource
import com.jeliliadesina.moviedir.api.MovieDbService

class MovieRemoteDataSource (private val service: MovieDbService) : BaseDataSource() {

    suspend fun fetchMovies(page: Int, language: String? = null, region: String? = null) =
        getResult { service.getPopular(page, language, region) }

    suspend fun fetchMovie(id: Int) =
        getResult { service.getMovie(id) }
}