package com.example.movies.interceptors

import com.example.movies.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor: Interceptor {

    private val apiKeyQueryParameterKey = "api_key"

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        //api key needed to make request calls
        val url = originalUrl.newBuilder()
            .addQueryParameter(apiKeyQueryParameterKey, BuildConfig.THE_MOVIE_DB_API_TOKEN)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(url)
            .build()

        return chain.proceed(newRequest)
    }
}