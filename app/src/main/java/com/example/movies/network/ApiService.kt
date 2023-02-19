package com.example.movies.network

import com.example.movies.model.MovieDetails
import com.example.movies.model.PopularMovies
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/3/movie/popular")
    suspend fun fetchPopularMovies(): Response<PopularMovies>

    @GET("/3/movie/{movie_id}")
    suspend fun fetchMovieDetails(@Path("movie_id") id: Long): Response<MovieDetails>

    companion object {
        val instance: ApiService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpProvider.getOkHttpClient())
                .build()

            retrofit.create(ApiService::class.java)
        }
    }

}
