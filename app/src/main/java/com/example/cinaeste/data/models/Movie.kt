package com.example.cinaeste.data.models

data class Movie (
    var id: Long,
    var title: String,
    var overview: String,
    var releaseDate: String,
    var homepage: String?,
    var genre: String?,
    var posterPath: String,
    var backdropPath: String
)