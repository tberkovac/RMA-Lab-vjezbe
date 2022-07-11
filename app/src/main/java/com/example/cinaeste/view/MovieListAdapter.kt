package com.example.cinaeste.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinaeste.R
import com.example.cinaeste.data.models.Movie


class MovieListAdapter(
    private var movies: List<Movie>,
    private val onItemClicked: (movie:Movie,view1:View,view2:View) -> Unit
) : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.kartice_elementi, parent, false)
        return MovieViewHolder(view)
    }

    private val posterPath = "https://image.tmdb.org/t/p/w342"
    override fun getItemCount(): Int = movies.size
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.movieTitle.text = movies[position].title;
        val context: Context = holder.movieImage.getContext()

        Glide.with(context)
            .load(posterPath + movies[position].posterPath)
            .centerCrop()
            .placeholder(R.drawable.defaultslika)
            .error(R.drawable.defaultslika)
            .fallback(R.drawable.defaultslika)
            .into(holder.movieImage);

        holder.itemView.setOnClickListener{ onItemClicked(movies[position],holder.movieImage,holder.movieTitle) }

    }
    fun updateMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }
    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieImage: ImageView = itemView.findViewById(R.id.movieImage)
        val movieTitle: TextView = itemView.findViewById(R.id.movieTitle)

    }
}
