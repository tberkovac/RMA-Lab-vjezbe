package com.example.lv1drugiput

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lv1drugiput.data.Movie
import com.example.lv1drugiput.view.MovieListAdapter
import com.example.lv1drugiput.viewmodel.MovieListViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var favoriteMovies: RecyclerView
    private lateinit var recentMovies: RecyclerView
    private lateinit var favoriteMoviesAdapter: MovieListAdapter
    private lateinit var recentMoviesAdapter: MovieListAdapter
    private var movieListViewModel = MovieListViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        favoriteMovies = findViewById(R.id.favoriteMovies)
        recentMovies = findViewById(R.id.recentMovies)
        favoriteMovies.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recentMovies.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        favoriteMoviesAdapter = MovieListAdapter(arrayListOf()){
            movie -> showMovieDetails(movie)
        }
        recentMoviesAdapter = MovieListAdapter(arrayListOf()){
            movie -> showMovieDetails(movie)
        }
        favoriteMovies.adapter = favoriteMoviesAdapter
        recentMovies.adapter = recentMoviesAdapter
        favoriteMoviesAdapter.updateMovies(movieListViewModel.getFavoriteMovies())
        recentMoviesAdapter.updateMovies(movieListViewModel.getRecentMovies())
    }

    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java).apply {
            putExtra("movie_title", movie.title)
        }
        startActivity(intent)
    }

}