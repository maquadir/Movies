package com.example.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.movies.model.MovieDetails
import com.example.movies.model.PopularMovies
import com.example.movies.network.ApiService
import com.example.movies.repository.MoviesRepository
import com.example.movies.viewmodel.IMovies
import com.example.movies.viewmodel.MainViewModel
import com.example.movies.viewmodel.MoviesController
import com.mifmif.common.regex.Main
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*

class MainViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: MoviesRepository
    private lateinit var moviesController: IMovies
    private lateinit var viewModel: MainViewModel
    private lateinit var popularMovies: PopularMovies
    private lateinit var movieDetails: MovieDetails
    private lateinit var loadingObserver: Observer<Boolean>
    private lateinit var moviesObserver: Observer<PopularMovies?>
    private lateinit var movieDetailObserver: Observer<MovieDetails?>
    private lateinit var errorObserver: Observer<Boolean>

    @Before
    fun setup() {
        repository = mock()
        moviesController = mock()
        viewModel = MainViewModel(moviesController)
        popularMovies = mock()
        movieDetails = mock()
        loadingObserver = mock()
        moviesObserver = mock()
        errorObserver = mock()
        movieDetailObserver = mock()

        viewModel.loaderLiveData.observeForever(loadingObserver)
        viewModel.errorLiveData.observeForever(errorObserver)
        viewModel.popularMoviesLiveData.observeForever(moviesObserver)
        viewModel.movieDetailsLiveData.observeForever(movieDetailObserver)
    }

    @Test
    fun init_fetchPopularMovies() {
        verify(moviesController).getPopularMovies(any<IMovies.Callback>())
    }

    @Test
    fun init_shouldShowLoading() {
        viewModel.fetchPopularMovies()

        verify(loadingObserver).onChanged(eq(true))
    }

    @Test
    fun init_shouldShowError_whenControllerReturnsError() {
        setupPopularMoviesWithError()

        viewModel.fetchPopularMovies()

        verify(errorObserver).onChanged(eq(true))
    }

    @Test
    fun init_shouldHideLoading_whenFactoryReturnsError() {
        setupPopularMoviesWithError()

        viewModel.fetchPopularMovies()

        verify(loadingObserver).onChanged(eq(false))
    }

    @Test
    fun init_shouldHideError_whenControllerReturnsSuccess() {
        setupPopularMoviesWithSuccess()

        viewModel.fetchPopularMovies()

        verify(errorObserver).onChanged(eq(false))
    }

    @Test
    fun init_shouldHideLoading_whenControllerReturnsSuccess() {
        setupPopularMoviesWithSuccess()

        viewModel.fetchPopularMovies()

        verify(loadingObserver).onChanged(eq(false))
    }

    @Test
    fun init_shouldShowPopularMovies_whenControllerReturnsSuccess() {
        val mockPopularMovies = mock<PopularMovies>()
        whenever(popularMovies).thenReturn(mockPopularMovies)
        setupPopularMoviesWithSuccess()

        viewModel.fetchPopularMovies()

        verify(moviesObserver).onChanged(eq(mockPopularMovies))
    }


    private fun setupPopularMoviesWithSuccess() {
        doAnswer {
            val callback: IMovies.Callback =
                it.getArgument(0)
            callback.onSuccess(true, popularMovies)
        }.whenever(moviesController).getPopularMovies(any())
    }

    private fun setupPopularMoviesWithError() {
        doAnswer {
            val callback: IMovies.Callback =
                it.getArgument(0)
            callback.onError(false)
        }.whenever(moviesController).getPopularMovies(any())
    }

    //MOVIE DETAILS

    @Test
    fun init_fetchMovieDetails() {
        viewModel.fetchMovieDetails(any())
        verify(moviesController).getMovieDetails(any(), any<IMovies.Callback>())
    }

    @Test
    fun init_shouldShowLoadingUponMovieDetails() {
        viewModel.fetchMovieDetails(any())

        verify(loadingObserver).onChanged(eq(true))
    }

    @Test
    fun init_shouldShowError_whenControllerReturnsError_in_movieDetails() {
        setupPopularMoviesWithError_in_movieDetails()

        viewModel.fetchMovieDetails(any())

        verify(errorObserver).onChanged(eq(true))
    }

    @Test
    fun init_shouldHideLoading_whenFactoryReturnsError_in_movieDetails() {
        setupPopularMoviesWithError_in_movieDetails()

        viewModel.fetchMovieDetails(any())

        verify(loadingObserver).onChanged(eq(false))
    }

    @Test
    fun init_shouldHideError_whenControllerReturnsSuccess_in_movieDetails() {
        setupPopularMoviesWithSuccess_in_movieDetails()

        viewModel.fetchMovieDetails(any())

        verify(errorObserver).onChanged(eq(false))
    }

    @Test
    fun init_shouldHideLoading_whenControllerReturnsSuccess_in_movieDetails() {
        setupPopularMoviesWithSuccess_in_movieDetails()

        viewModel.fetchMovieDetails(any())

        verify(loadingObserver).onChanged(eq(false))
    }

    @Test
    fun init_shouldShowPopularMovies_whenControllerReturnsSuccess_in_movieDetails() {
        val mockMovieDetails = mock<MovieDetails>()
        whenever(movieDetails).thenReturn(mockMovieDetails)
        setupPopularMoviesWithSuccess()

        viewModel.fetchMovieDetails(any())

        verify(movieDetailObserver).onChanged(eq(mockMovieDetails))
    }

    private fun setupPopularMoviesWithSuccess_in_movieDetails() {
        doAnswer {
            val callback: IMovies.Callback =
                it.getArgument(0)
            callback.onSuccess(true, movieDetails)
        }.whenever(moviesController).getMovieDetails(any(), any())
    }

    private fun setupPopularMoviesWithError_in_movieDetails() {
        doAnswer {
            val callback: IMovies.Callback =
                it.getArgument(0)
            callback.onError(false)
        }.whenever(moviesController).getMovieDetails(any(), any())
    }
}