package com.example.movies

import com.example.movies.model.PopularMovies
import com.example.movies.network.ApiService
import com.example.movies.repository.MoviesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class MoviesRepositoryUnitTests {

    private lateinit var moviesRepository: MoviesRepository
    private lateinit var apiService: ApiService

    @Before
    fun setup() {
        apiService = mock()
        moviesRepository = MoviesRepository(apiService)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fetchPopularMoviesFromApi() = runTest {
        whenever(apiService.fetchPopularMovies())
            .thenReturn(Response.success(UtilsTest.popularMovies))
        val response = moviesRepository.fetchPopularMovie()
        assertEquals(UtilsTest.popularMovies, response.body())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fetchMovieDetailsFromApi() = runTest {
        val movieId = UtilsTest.popularMovies.results[0].id.toLong()
        whenever(apiService.fetchMovieDetails(movieId))
            .thenReturn(Response.success(UtilsTest.movieDetails))
        val response = moviesRepository.fetchMovieDetails(movieId)
        assertEquals(UtilsTest.movieDetails, response.body())
    }
}