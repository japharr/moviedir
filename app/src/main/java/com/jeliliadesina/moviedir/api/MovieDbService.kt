package com.jeliliadesina.moviedir.api

import com.jeliliadesina.moviedir.movie.data.Movie
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response
import retrofit2.http.Path

interface MovieDbService {
    companion object {
        const val ENDPOINT = "https://api.themoviedb.org/3/"
    }

    @GET("movie/popular")
    suspend fun getPopular(@Query("page") page: Int? = null,
                          @Query("region") region: String? = null,
                          @Query("language") language: String? = null): Response<ResultsResponse<Movie>>

    @GET("movie/{id}")
    suspend fun getMovie(@Path("id") id: Int): Response<Movie>
}