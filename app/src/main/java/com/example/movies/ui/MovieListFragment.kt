package com.example.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.R
import com.example.movies.databinding.FragmentMovieListBinding
import com.example.movies.model.PopularMovies
import com.example.movies.network.ApiService
import com.example.movies.repository.MoviesRepository
import com.example.movies.viewmodel.MainViewModel
import com.example.movies.viewmodel.MainViewModelFactory
import com.example.movies.viewmodel.MoviesController

class MovieListFragment : Fragment(), RecyclerViewItemClickListener {

    private lateinit var binding: FragmentMovieListBinding
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
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.movieRecyclerview.layoutManager = LinearLayoutManager(activity)

        //display states in ui
        activity?.let { activity ->
            sharedViewModel.popularMoviesLiveData.observe(activity) {
                when {
                    it?.results?.isEmpty() == true -> showEmptyDataState()
                    it?.results?.isNotEmpty() == true -> showMovieList(it)
                    else -> showErrorState()
                }

            }
        }

        //handle progress bar
        activity?.let { activity ->
            sharedViewModel.loaderLiveData.observe(activity) {
                binding.progressBar.isVisible = it
            }
        }
    }

    private fun showEmptyDataState() {
        binding.apply {
            movieRecyclerview.visibility = View.GONE
            progressBar.visibility = View.GONE
            textview.visibility = View.VISIBLE
            textview.text = getString(R.string.no_data_available)
        }
    }

    private fun showMovieList(popularMovies: PopularMovies) {
        binding.let {
            it.movieRecyclerview.visibility = View.VISIBLE
            it.movieRecyclerview.adapter = MovieAdapter(popularMovies, this)
            it.progressBar.visibility = View.GONE
            it.textview.visibility = View.GONE
        }
    }

    private fun showErrorState() {
        binding.apply {
            movieRecyclerview.visibility = View.GONE
            progressBar.visibility = View.GONE
            textview.visibility = View.VISIBLE
            textview.text = getString(R.string.something_went_wrong)
        }
    }

    override fun showMovieDetails(movieId: Int) {
        val directions =
            MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(
                movieId.toLong()
            )
        findNavController().navigate(directions)
    }
}