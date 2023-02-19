package com.example.movies.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.*
import com.example.movies.model.MovieDetails
import com.example.movies.model.PopularMovies
import kotlinx.coroutines.*

class MainViewModel(private val moviesController: IMovies) : ViewModel() {

    private val popularMoviesMutableLiveData = MutableLiveData<PopularMovies?>()
    val popularMoviesLiveData: LiveData<PopularMovies?>
        get() = popularMoviesMutableLiveData

    private val movieDetailsMutableLiveData = MutableLiveData<MovieDetails?>()
    val movieDetailsLiveData: LiveData<MovieDetails?>
        get() = movieDetailsMutableLiveData

    private val mutableLoader = MutableLiveData(true)
    val loaderLiveData: LiveData<Boolean>
        get() = mutableLoader

    private val errorMutableLiveData = MutableLiveData(false)
    val errorLiveData: LiveData<Boolean>
        get() = errorMutableLiveData

    init {
        fetchPopularMovies()
    }

    fun fetchPopularMovies() {
        moviesController.getPopularMovies(object : IMovies.Callback {
            override fun <T> onSuccess(bool: Boolean, data: T?) {
                popularMoviesMutableLiveData.value = data as PopularMovies
                mutableLoader.value = false
            }

            override fun onError(bool: Boolean) {
                popularMoviesMutableLiveData.value = null
                mutableLoader.value = false
                errorMutableLiveData.value = true
            }
        })
    }

    fun fetchMovieDetails(movie_id: Long) {
        moviesController.getMovieDetails(movie_id, object : IMovies.Callback {
            override fun <T> onSuccess(bool: Boolean, data: T?) {
                movieDetailsMutableLiveData.value = data as MovieDetails
                mutableLoader.value = false
            }

            override fun onError(bool: Boolean) {
                movieDetailsMutableLiveData.value = null
                mutableLoader.value = false
                errorMutableLiveData.value = true
            }
        })
    }
}