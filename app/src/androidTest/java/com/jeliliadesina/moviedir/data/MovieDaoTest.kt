package com.jeliliadesina.moviedir.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jeliliadesina.moviedir.movie.data.MovieDao
import com.jeliliadesina.moviedir.util.getValue
import com.jeliliadesina.moviedir.util.testMovieA
import com.jeliliadesina.moviedir.util.testMovieB
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDaoTest: DbTest() {
    private lateinit var movieDao: MovieDao
    private val movieA = testMovieA.copy()
    private val movieB = testMovieB.copy()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        movieDao = db.movieDao()

        runBlocking {
            movieDao.insertAll(listOf(movieA, movieB))
        }
    }

    @Test
    fun testGetMovies() {
        val list = getValue(movieDao.getMovies())
        assertThat(list.size, equalTo(2))

        assertThat(list[0], equalTo(movieA))
        assertThat(list[1], equalTo(movieB))
    }

    @Test
    fun testGetMovie() {
        assertThat(getValue(movieDao.getMovie(movieA.id)), equalTo(movieA))
    }
}