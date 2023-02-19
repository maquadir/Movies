package com.example.movies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.model.MovieDetails
import com.example.movies.model.PopularMovies
import com.example.movies.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

interface IMovies {
    fun getPopularMovies(callback: Callback)
    fun getMovieDetails(movie_id: Long, callback: Callback)

    interface Callback {
        fun <T> onSuccess(bool:Boolean, data:T?)
        fun onError(bool:Boolean)
    }
}


class MoviesController(
    private val moviesRepository: MoviesRepository,
    private val appDispatcher: Dispatchers = Dispatchers
) : IMovies, ViewModel() {

    override fun getPopularMovies(callback: IMovies.Callback) {
        viewModelScope.launch(appDispatcher.Main) {
            try {
                val popularMovies =
                    withContext(appDispatcher.IO) { moviesRepository.fetchPopularMovie() }
                callback.onSuccess(true, popularMovies.body())
            } catch (e: Exception) {
                callback.onError(false)
            }
        }
    }

    override fun getMovieDetails(movie_id: Long, callback: IMovies.Callback) {
        viewModelScope.launch(appDispatcher.Main) {
            try {
                val movieInfo =
                    withContext(appDispatcher.IO) { moviesRepository.fetchMovieDetails(movie_id) }
                callback.onSuccess(true, movieInfo.body())
            } catch (e: Exception) {
                callback.onError(false)
            }
        }
    }
}