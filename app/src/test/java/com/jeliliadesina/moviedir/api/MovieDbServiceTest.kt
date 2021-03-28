package com.jeliliadesina.moviedir.api

import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@RunWith(JUnit4::class)
class MovieDbServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: MovieDbService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieDbService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun requestMovies() {
        runBlocking {
            enqueueResponse("popular_movies.json")
            val resultResponse = service.getPopular().body()

            val request = mockWebServer.takeRequest()
            assertNotNull(resultResponse)
            assertThat(request.path, `is`("/movie/popular"))
        }
    }

    @Test
    fun getLegoSetsResponse() {
        runBlocking {
            enqueueResponse("popular_movies.json")
            val resultResponse = service.getPopular().body()
            val movies = resultResponse!!.results

            assertThat(resultResponse.page, `is`(500))
            assertThat(movies.size, `is`(20))
        }
    }

    @Test
    fun getMovieItem() {
        runBlocking {
            enqueueResponse("popular_movies.json")
            val resultResponse = service.getPopular().body()
            val movies = resultResponse!!.results

            val movie = movies[0]
            assertThat(movie.id, `is`(11962))
            assertThat(movie.title, `is`("Joe's Apartment"))
            assertThat(movie.voteCount, `is`(216))
            assertThat(movie.originalLanguage, `is`("en"))
            assertThat(movie.releaseDate, `is`("1996-07-26"))
            assertThat(movie.posterPath, `is`("/7OBbURUtU37ucxHGUVkD598f6t3.jpg"))
        }
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse.setBody(
            source.readString(Charsets.UTF_8))
        )
    }
}