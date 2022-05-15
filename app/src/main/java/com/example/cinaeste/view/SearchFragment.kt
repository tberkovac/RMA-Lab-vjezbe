package com.example.cinaeste.view

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinaeste.MovieDetailActivity
import com.example.cinaeste.R
import com.example.cinaeste.data.models.Movie
import android.util.Pair
import com.example.cinaeste.data.repositories.MovieRepository
import com.example.cinaeste.viewmodel.MovieListViewModel
import java.net.URI.create

class SearchFragment : Fragment() {
    private lateinit var searchText: EditText
    private lateinit var searchButton: ImageButton
    private lateinit var listaPretrazenihFilmova: RecyclerView
    private lateinit var movieListViewModel : MovieListViewModel
    private lateinit var searchMoviesAdapter : MovieListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.search_fragment, container, false)
        movieListViewModel = MovieListViewModel(this@SearchFragment::searchDone,this@SearchFragment::onError)
        searchMoviesAdapter = MovieListAdapter(arrayListOf()) { movie,view1,view2 -> showMovieDetails(movie,view1,view2)}
        searchText = view.findViewById(R.id.searchText)
        searchButton = view.findViewById(R.id.searchButton)
        listaPretrazenihFilmova = view.findViewById(R.id.listaPretrazenihFilmova)
        arguments?.getString("search")?.let {
            searchText.setText(it)
        }
        listaPretrazenihFilmova.layoutManager = GridLayoutManager(activity, 2)
        listaPretrazenihFilmova.adapter = searchMoviesAdapter

        searchButton.setOnClickListener { onClick() }
        return view
    }

    private fun onClick() {
        val toast = Toast.makeText(context, "Search start", Toast.LENGTH_SHORT)
        toast.show()
        movieListViewModel.search(searchText.text.toString())
    }

    fun searchDone(movies:List<Movie>){
        val toast = Toast.makeText(context, "Search done", Toast.LENGTH_SHORT)
        toast.show()
        searchMoviesAdapter.updateMovies(movies)
    }

    fun onError() {
        val toast = Toast.makeText(context, "Search error", Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun showMovieDetails(movie: Movie, view1: View, view2:View) {
        val intent = Intent(activity, MovieDetailActivity::class.java).apply {
            putExtra("movie_id", movie.id)
        }
        val options = ActivityOptions
            .makeSceneTransitionAnimation(activity,  Pair.create(view1, "poster"),
                Pair.create(view2, "title"))
        startActivity(intent, options.toBundle())
    }

    companion object {
        fun newInstance(search:String): SearchFragment = SearchFragment().apply {
            arguments = Bundle().apply {
                putString("search", search)
            }
        }
    }
}