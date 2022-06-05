package com.example.cinaeste.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinaeste.R
import com.example.cinaeste.data.models.Movie
import com.example.cinaeste.viewmodel.MovieDetailViewModel

class SimilarFragment(movieName:String,movieId:Long?,favourite:Boolean): Fragment() {
    private var favourite = favourite
    private var movieName:String = movieName
    private var movieId:Long? = movieId


    private lateinit var movieRV:RecyclerView
    private var movieList= listOf<Movie>()
    private lateinit var actorsRVSimpleSimilarAdapter: SimpleSimilarStringAdapter
    private var movieDetailViewModel =  MovieDetailViewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.similar_fragment, container, false)
        movieRV = view.findViewById<RecyclerView>(R.id.listSimilar)
        movieRV.layoutManager = LinearLayoutManager(activity)
        actorsRVSimpleSimilarAdapter = SimpleSimilarStringAdapter(movieList)
        movieRV.adapter = actorsRVSimpleSimilarAdapter
        if(favourite){
            movieId?.let { movieDetailViewModel.getSimilarMoviesByIdDB(requireContext(),it,onSuccess = ::onSuccess, onError = ::onError) }
        }else{
            movieId?.let { movieDetailViewModel.getSimilarMoviesById(it,onSuccess = ::onSuccess,
                onError = ::onError) }
        }

        return view
    }

    fun onSuccess(movies:List<Movie>){
        movieList=movies
        actorsRVSimpleSimilarAdapter.list = movies;
        actorsRVSimpleSimilarAdapter.notifyDataSetChanged();
    }
    fun onError() {
        val toast = Toast.makeText(context, "Search error", Toast.LENGTH_SHORT)
        toast.show()
    }
}