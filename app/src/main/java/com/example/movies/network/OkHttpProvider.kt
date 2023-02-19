package com.example.movies.network

import com.example.movies.interceptors.ApiKeyInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.util.concurrent.TimeUnit

object OkHttpProvider {

  private const val REQUEST_TIMEOUT = 3L

  private var okHttpClient: OkHttpClient? = null

  fun getOkHttpClient(): OkHttpClient {
    return if (okHttpClient == null) {
      val loggingInterceptor = HttpLoggingInterceptor { message -> Timber.tag("OkHttp").d(message) }
      loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
      loggingInterceptor.redactHeader("x-amz-cf-id")

      //add api_key interceptor
      val okHttpClient = OkHttpClient.Builder()
          .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
          .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
          .addInterceptor(ApiKeyInterceptor())
          .addInterceptor(loggingInterceptor)
          .build()
      OkHttpProvider.okHttpClient = okHttpClient
      okHttpClient
    } else {
      okHttpClient!!
    }
  }
}