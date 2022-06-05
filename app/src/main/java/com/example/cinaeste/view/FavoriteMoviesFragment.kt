package com.example.cinaeste.view

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinaeste.MovieDetailActivity
import com.example.cinaeste.R
import com.example.cinaeste.data.models.Movie
import com.example.cinaeste.viewmodel.MovieListViewModel
import android.util.Pair as UtilPair

class FavoriteMoviesFragment : Fragment() {
    private lateinit var favoriteMovies: RecyclerView
    private lateinit var favoriteMoviesAdapter: MovieListAdapter
    private var movieListViewModel = MovieListViewModel()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.favorites_fragment, container, false)
        favoriteMovies = view.findViewById(R.id.favoriteMovies)
        favoriteMovies.layoutManager = GridLayoutManager(activity, 2)
        favoriteMoviesAdapter = MovieListAdapter(arrayListOf()) { movie,view1,view2 -> showMovieDetails(movie,view1,view2) }

        favoriteMovies.adapter=favoriteMoviesAdapter

        context?.let {
            movieListViewModel.getFavorites(
                it,onSuccess = ::onSuccess,
                onError = ::onError)
        }

        return view;
    }
    companion object {
        fun newInstance(): FavoriteMoviesFragment = FavoriteMoviesFragment()
    }
    private fun showMovieDetails(movie: Movie, view1: View, view2: View) {
        val intent = Intent(activity, MovieDetailActivity::class.java).apply {
            putExtra("movie", movie)
            Log.v("FILM JE", movie.toString())
        }
        val options = ActivityOptions
                    .makeSceneTransitionAnimation(activity,  UtilPair.create(view1, "poster"),
                        UtilPair.create(view2, "title"))
        startActivity(intent, options.toBundle())
    }

    fun onSuccess(movies:List<Movie>){
        favoriteMoviesAdapter.updateMovies(movies)
    }
    fun onError() {
        val toast = Toast.makeText(context, "Error", Toast.LENGTH_SHORT)
        toast.show()
    }

}