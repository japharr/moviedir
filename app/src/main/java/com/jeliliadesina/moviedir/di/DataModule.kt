package com.jeliliadesina.moviedir.di

import android.content.Context
import com.jeliliadesina.moviedir.Constants.APP_DATABASE_NAME
import com.jeliliadesina.moviedir.api.MovieDbService
import com.jeliliadesina.moviedir.data.AppDatabase
import com.jeliliadesina.moviedir.movie.data.MovieDao
import com.jeliliadesina.moviedir.movie.data.MoviePageDataSource
import com.jeliliadesina.moviedir.movie.data.MovieRemoteDataSource
import com.jeliliadesina.moviedir.movie.data.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        AppDatabase.getInstance(context, APP_DATABASE_NAME)

    @Singleton
    @Provides
    fun provideMovieDao(appDatabase: AppDatabase) =
        appDatabase.movieDao()

    @Singleton
    @Provides
    fun provideMovieRemoteDataSource(apiService: MovieDbService) =
        MovieRemoteDataSource(apiService)

    @Singleton
    @Provides
    fun provideMoviePageDataSource(dataSource: MovieRemoteDataSource, dao: MovieDao, scope: CoroutineScope) =
        MoviePageDataSource(dataSource, dao, scope)

    @Singleton
    @Provides
    fun provideMovieRepository(movieDao: MovieDao, movieRemoteDataSource: MovieRemoteDataSource) =
        MovieRepository(movieDao, movieRemoteDataSource)
}