package com.example.movies.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.Utils
import com.example.movies.databinding.FragmentMovieDetailsBinding
import com.example.movies.model.MovieDetails
import com.example.movies.network.ApiService
import com.example.movies.repository.MoviesRepository
import com.example.movies.viewmodel.MainViewModel
import com.example.movies.viewmodel.MainViewModelFactory
import com.example.movies.viewmodel.MoviesController

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private lateinit var binding: FragmentMovieDetailsBinding
    private val apiService = ApiService.instance
    private val moviesRepository = MoviesRepository(apiService)

    private val sharedViewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(MoviesController(moviesRepository))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments ?: return

        // Retrieve passed arguments and display them
        val args = MovieDetailsFragmentArgs.fromBundle(bundle)
        val movieId = args.movieId

        //fetch movie details from network
        sharedViewModel.fetchMovieDetails(movieId)

        //display states in ui
        activity?.let { activity ->
            sharedViewModel.movieDetailsLiveData.observe(activity) {
                if (it == null) {
                    showErrorState()
                } else {
                    showMovieDetails(it)
                }
            }
        }

    }

    private fun showMovieDetails(movieDetails: MovieDetails) {
        binding.apply {
            movieDetailsContainer.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            textview.visibility = View.GONE
        }

        Glide.with(binding.posterImageView)
            .load(Utils.getPostUrl(movieDetails.poster_path))
            .centerCrop()
            .into(binding.posterImageView)

        Glide.with(binding.backdropImageView)
            .load(Utils.getPostUrl(movieDetails.backdrop_path))
            .centerCrop()
            .into(binding.backdropImageView)

        binding.movieNameTextview.text = movieDetails.title

        if (movieDetails.tagline?.isEmpty() == true) {
            binding.movieTaglineTextview.visibility = View.GONE
        }
        binding.movieTaglineTextview.text = movieDetails.tagline
        binding.movieOverviewTextview.text = movieDetails.overview
        binding.movieVoteTextview.text =
            getString(R.string.vote_average_s, movieDetails.vote_average.toString())
        binding.movieRdateTextview.text =
            getString(R.string.release_date, movieDetails.release_date)
    }

    private fun showErrorState() {
        binding.apply {
            movieDetailsContainer.visibility = View.GONE
            progressBar.visibility = View.GONE
            textview.visibility = View.VISIBLE
            textview.text = getString(R.string.something_went_wrong)
        }
    }
}