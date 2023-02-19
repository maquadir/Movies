package com.example.movies.repository

import com.example.movies.network.ApiService

class MoviesRepository(private val apiService: ApiService) {

    //get popular movies
    suspend fun fetchPopularMovie() =
        apiService.fetchPopularMovies()

    //get movie details
    suspend fun fetchMovieDetails(movie_id: Long) =
        apiService.fetchMovieDetails(movie_id)
}
