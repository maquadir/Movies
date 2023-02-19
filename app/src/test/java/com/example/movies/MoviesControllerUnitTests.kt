package com.example.movies

import com.example.movies.model.PopularMovies
import com.example.movies.model.Result
import com.example.movies.network.ApiService
import com.example.movies.repository.MoviesRepository
import com.example.movies.viewmodel.IMovies
import com.example.movies.viewmodel.MoviesController
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.doAnswer
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class MoviesControllerUnitTests {

    private lateinit var moviesRepository: MoviesRepository
    private lateinit var moviesController: MoviesController

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        moviesRepository = mock()
        moviesController = MoviesController(moviesRepository, Dispatchers)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
     fun fetchPopularMoviesFromRepo() = runTest {
        moviesController.getPopularMovies(mock<IMovies.Callback>())

        verify(moviesRepository).fetchPopularMovie()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
     fun fetchMovieDetailsFromRepo() = runTest {
        moviesController.getMovieDetails(1, mock<IMovies.Callback>())

        verify(moviesRepository).fetchMovieDetails(1)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
     fun fetchPopularMovies_shouldCallOnSuccess() = runTest {
        val callback = mock<IMovies.Callback>()
        setUpRepositoryWithPopularMovies(moviesRepository)

        moviesController.getPopularMovies(callback)

        verify(callback).onSuccess(true, UtilsTest.popularMovies)
    }

     @OptIn(ExperimentalCoroutinesApi::class)
     fun setUpRepositoryWithPopularMovies(moviesRepository: MoviesRepository) = runTest {
        doAnswer {
            val callback: IMovies.Callback = it.getArgument(0)
            callback.onSuccess(true, UtilsTest.popularMovies)
        }.whenever(moviesRepository).fetchPopularMovie()
    }


}