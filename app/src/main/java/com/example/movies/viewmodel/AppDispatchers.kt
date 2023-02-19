package com.example.movies.viewmodel

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object AppDispatchers {
    val IO: CoroutineDispatcher = Dispatchers.IO
    val MAIN: CoroutineDispatcher = Dispatchers.Main
}