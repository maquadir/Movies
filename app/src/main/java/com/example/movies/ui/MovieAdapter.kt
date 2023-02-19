package com.example.movies.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.Utils
import com.example.movies.databinding.RowMovieViewholderBinding
import com.example.movies.model.PopularMovies

class MovieAdapter(
    private val popularMovies: PopularMovies,
    private val recyclerViewItemClickListener: RecyclerViewItemClickListener
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
    val binding = RowMovieViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return MovieViewHolder(binding)
  }

  override fun getItemCount() = popularMovies.results.size

  override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
    val movie = popularMovies.results[position]
    with(holder) {
      Glide.with(moviePosterImageView)
          .load(Utils.getPostUrl(movie.poster_path))
          .into(moviePosterImageView)

      movieNameTextView.text = movie.title

        //display details of a movie item
      rootView.setOnClickListener {
        recyclerViewItemClickListener.showMovieDetails(movie.id)
      }
    }
  }

  inner class MovieViewHolder(binding: RowMovieViewholderBinding) : RecyclerView.ViewHolder(binding.root) {
    val movieNameTextView = binding.movieNameTextview
    val moviePosterImageView = binding.moviePosterImageview
    val rootView = binding.root
  }
}