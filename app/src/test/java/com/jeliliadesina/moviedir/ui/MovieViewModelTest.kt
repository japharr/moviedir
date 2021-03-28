package com.jeliliadesina.moviedir.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jeliliadesina.moviedir.movie.data.MovieDao
import com.jeliliadesina.moviedir.movie.data.MovieRepository
import com.jeliliadesina.moviedir.movie.ui.MoviesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class MovieViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val repository = mock(MovieRepository::class.java)
    private var viewModel = MoviesViewModel(repository, coroutineScope)

    @Test
    fun testNull() {
        assertThat(viewModel.connectivityAvailable, notNullValue())
        verify(repository, never()).observePagedSets(false, coroutineScope)
        verify(repository, never()).observePagedSets(true, coroutineScope)
    }

    @Test
    fun doNotFetchWithoutObservers() {
        verify(repository, never()).observePagedSets(false, coroutineScope)
    }

    @Test
    fun doNotFetchWithoutObserverOnConnectionChange() {
        viewModel.connectivityAvailable.set(true)

        verify(repository, never()).observePagedSets(true, coroutineScope)
    }

}