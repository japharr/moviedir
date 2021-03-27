package com.jeliliadesina.moviedir.di

import com.jeliliadesina.moviedir.Constants.APP_DATABASE_NAME
import com.jeliliadesina.moviedir.data.AppDatabase
import com.jeliliadesina.moviedir.movie.data.MoviePageDataSource
import com.jeliliadesina.moviedir.movie.data.MovieRemoteDataSource
import com.jeliliadesina.moviedir.movie.data.MovieRepository
import org.koin.dsl.module

val dataModule = module {
    single { AppDatabase.getInstance(get(), APP_DATABASE_NAME) }

    single { (get() as AppDatabase).movieDao() }

    single { MoviePageDataSource(get(), get(), get()) }

    single { MovieRemoteDataSource(get()) }

    single { MovieRepository(get(), get()) }
}
