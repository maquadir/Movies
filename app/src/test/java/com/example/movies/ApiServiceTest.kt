package com.example.movies

import com.example.movies.network.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.MatcherAssert.assertThat
import org.junit.*
import org.junit.Assert.assertEquals
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceTestUsingMockWebServer {

    private lateinit var mockWebServer: MockWebServer

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val apiService by lazy {
        retrofit.create(ApiService::class.java)
    }


    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun checkPopularMoviesApiCall() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setBody(Gson().toJson(UtilsTest.popularMovies))
                .setResponseCode(200)
        )

        apiService.fetchPopularMovies()
        assertEquals(
            "/3/movie/popular",
            mockWebServer.takeRequest().path
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun checkMovieDetailsApiCall() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setBody(Gson().toJson(UtilsTest.movieDetails))
                .setResponseCode(200)
        )

        val movieId = UtilsTest.popularMovies.results[0].id.toLong()
        apiService.fetchMovieDetails(movieId)
        assertEquals(
            "/3/movie/${UtilsTest.popularMovies.results[0].id}",
            mockWebServer.takeRequest().path
        )
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getPopularMoviesUsingMockServer() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setBody(Gson().toJson(UtilsTest.popularMovies))
                .setResponseCode(200)
        )

        val response = apiService.fetchPopularMovies()
        assertEquals(response.body(), UtilsTest.popularMovies)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getMovieDetailsUsingMockServer() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setBody(Gson().toJson(UtilsTest.movieDetails))
                .setResponseCode(200)
        )

        val movieId = UtilsTest.popularMovies.results[0].id.toLong()
        val response = apiService.fetchMovieDetails(movieId)
        assertEquals(response.body(), UtilsTest.movieDetails)
    }

}