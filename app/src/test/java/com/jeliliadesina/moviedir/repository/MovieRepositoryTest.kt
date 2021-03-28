package com.jeliliadesina.moviedir.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jeliliadesina.moviedir.api.MovieDbService
import com.jeliliadesina.moviedir.data.AppDatabase
import com.jeliliadesina.moviedir.movie.data.MovieDao
import com.jeliliadesina.moviedir.movie.data.MovieRemoteDataSource
import com.jeliliadesina.moviedir.movie.data.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito

@RunWith(JUnit4::class)
class MovieRepositoryTest {
    private lateinit var repository: MovieRepository
    private val dao = Mockito.mock(MovieDao::class.java)
    private val service = Mockito.mock(MovieDbService::class.java)
    private val remoteDataSource = MovieRemoteDataSource(service)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Before
    fun init() {
        val db = Mockito.mock(AppDatabase::class.java)
        Mockito.`when`(db.movieDao()).thenReturn(dao)
        Mockito.`when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        repository = MovieRepository(dao, remoteDataSource)
    }

    @Test
    fun loadMoviesFromNetwork() {
        runBlocking {
            repository.observePagedSets(connectivityAvailable = true, coroutineScope = coroutineScope)

            Mockito.verify(dao, Mockito.never()).getMovies()
            Mockito.verifyNoInteractions(dao)
        }
    }
}