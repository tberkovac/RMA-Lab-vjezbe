package com.example.cinaeste.viewmodel

import com.example.cinaeste.data.Movie
import com.example.cinaeste.data.MovieRepository

class MovieDetailViewModel {
    fun getMovieByTitle(name:String): Movie {
        var movies: ArrayList<Movie> = arrayListOf()
        movies.addAll(MovieRepository.getRecentMovies())
        movies.addAll(MovieRepository.getFavoriteMovies())
        val movie= movies.find { movie -> name.equals(movie.title) }
        //ako film ne postoji vratimo testni
        return movie?:Movie(0,"Test","Test","Test","Test","Test")
    }
}