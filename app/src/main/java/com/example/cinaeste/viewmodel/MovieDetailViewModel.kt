package com.example.cinaeste.viewmodel

import com.example.cinaeste.data.repositories.ActorMovieRepository
import com.example.cinaeste.data.models.Movie
import com.example.cinaeste.data.repositories.MovieRepository

class MovieDetailViewModel {

    fun getMovieByTitle(name:String):Movie{
        var movies: ArrayList<Movie> = arrayListOf()
        movies.addAll(MovieRepository.getRecentMovies())
        movies.addAll(MovieRepository.getFavoriteMovies())
        val movie= movies.find { movie -> name.equals(movie.title) }
        return movie?:Movie(0,"Test","Test","Test","Test","Test")
    }
    fun getActorsByTitle(name: String):List<String>{
        return ActorMovieRepository.getActorMovies()?.get(name)?: emptyList()
    }

    fun getSimilarByTitle(name: String):List<String>{
        return MovieRepository.getSimilarMovies()?.get(name)?: emptyList()
    }
}