package com.example.cinaeste

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.cinaeste.data.models.Movie
import com.example.cinaeste.view.ActorsFragment
import com.example.cinaeste.view.SimilarFragment
import com.example.cinaeste.viewmodel.MovieDetailViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView

class MovieDetailActivity : AppCompatActivity() {

    private var movieDetailViewModel =  MovieDetailViewModel()
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var movie: Movie
    private lateinit var title : TextView
    private lateinit var overview : TextView
    private lateinit var releaseDate : TextView
    private lateinit var genre : TextView
    private lateinit var website : TextView
    private lateinit var poster : ImageView
    private lateinit var shareButton : FloatingActionButton

    private val mOnItemSelectedListener = NavigationBarView.OnItemSelectedListener{ item ->
        when(item.itemId){
            R.id.navForDetails_list_of_actors -> {
                var actorsFragment = ActorsFragment(movie.title)
                openFragment(actorsFragment)
                return@OnItemSelectedListener true
            }
            R.id.navForDetails_similar_movies -> {
                var similarFragment = SimilarFragment(movie.title)
                openFragment(similarFragment)
                return@OnItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        title = findViewById(R.id.movie_title)
        overview = findViewById(R.id.movie_overview)
        releaseDate = findViewById(R.id.movie_release_date)
        genre = findViewById(R.id.movie_genre)
        poster = findViewById(R.id.movie_poster)
        website = findViewById(R.id.movie_website)
        shareButton = findViewById(R.id.shareButton)
        bottomNavigation = findViewById(R.id.detailNavigation)
        bottomNavigation.setOnItemSelectedListener(mOnItemSelectedListener)

        website.setOnClickListener{
            showWebsite()
        }
        title.setOnClickListener{
            youtubeSearch()
        }
        shareButton.setOnClickListener{
            shareOverview()
        }
        val extras = intent.extras

        if (extras != null) {
            movie = movieDetailViewModel.getMovieByTitle(extras.getString("movie_title", ""))
            populateDetails()
        } else {
            finish()
        }

        bottomNavigation.selectedItemId = R.id.navForDetails_list_of_actors
    }
    private fun populateDetails() {
        title.text=movie.title
        releaseDate.text=movie.releaseDate
        genre.text=movie.genre
        website.text=movie.homepage
        overview.text=movie.overview
        val context: Context = poster.context
        var id: Int = context.resources
            .getIdentifier(movie.genre, "drawable", context.packageName)
        if (id==0) id=context.resources
            .getIdentifier("picture1", "drawable", context.packageName)
        poster.setImageResource(id)
    }

    private fun showWebsite(){

        val webIntent: Intent = Uri.parse(movie.homepage).let { webpage ->
            Intent(Intent.ACTION_VIEW, webpage)
        }
        try {
            startActivity(webIntent)
        } catch (e: ActivityNotFoundException) {
            // Definisati naredbe ako ne postoji aplikacija za navedenu akciju
        }
    }

    private fun youtubeSearch(){
        val intent = Intent(Intent.ACTION_SEARCH).apply {
            setPackage("com.google.android.youtube")
            putExtra("query", movie.title + " trailer")
        }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // Definisati naredbe ako ne postoji aplikacija za navedenu akciju
        }
    }

    private fun shareOverview(){
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, movie.overview)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(intent, null)
        startActivity(shareIntent)
    }
    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.actorSimilarContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}