package com.example.movies

import com.example.movies.model.*

object UtilsTest {

    val results = listOf(
        Result(
            false,
            "",
            listOf(1, 2, 3),
            1,
            "en",
            "blackpanther",
            "fight for land",
            45.67,
            "",
            "2022-11-09",
            "blackpanterh:wakanda",
            false,
            7.5,
            2000
        ),
        Result(
            false,
            "",
            listOf(4, 5, 6),
            2,
            "en",
            "avengers",
            "fight for earth",
            65.67,
            "",
            "2022-06-09",
            "avengers:infinity",
            false,
            8.5,
            3000
        ),
        Result(
            false,
            "",
            listOf(7, 8, 9),
            3,
            "en",
            "batman",
            "fight for city",
            85.67,
            "",
            "2022-04-09",
            "batman:vengeance",
            false,
            9.5,
            5000
        ),
    )
    val popularMovies = PopularMovies(1, results, 5, 5)
    val movieDetails = MovieDetails(
        adult = false,
        backdrop_path = "",
        belongs_to_collection = null,
        budget = 0,
        genres = null,
        homepage = null,
        id = 1,
        imdb_id = null,
        original_language = "en",
        original_title = "blackpanther",
        overview = "fight for land",
        popularity = 45.67,
        poster_path = "",
        production_companies = null,
        production_countries = null,
        release_date = "2022 - 11 - 09",
        revenue = 0,
        runtime = 0,
        spoken_languages = null,
        status = null,
        tagline = null,
        title = "blackpanterh:wakanda",
        video = false,
        vote_average = 7.5,
        vote_count = 2000
    )
}