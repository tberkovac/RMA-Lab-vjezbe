package com.example.cinaeste

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.transition.Fade
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.cinaeste.data.models.Movie
import com.example.cinaeste.view.FavoriteMoviesFragment
import com.example.cinaeste.view.RecentMoviesFragment
import com.example.cinaeste.view.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView


class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView
    private val br: BroadcastReceiver = ConnectivityBroadcastReceiver()
    private val filter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
    private val mOnItemSelectedListener = NavigationBarView.OnItemSelectedListener{ item ->
        when (item.itemId) {
            R.id.navigation_favorites -> {
                val favoritesFragment = FavoriteMoviesFragment.newInstance()
                openFragment(favoritesFragment)
                return@OnItemSelectedListener true
            }
            R.id.navigation_recent -> {
                val recentFragments = RecentMoviesFragment.newInstance()
                openFragment(recentFragments)
                return@OnItemSelectedListener true
            }
            R.id.navigation_search -> {
                val searchFragment = SearchFragment.newInstance(" ")
                openFragment(searchFragment)
                return@OnItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        with(window) {
            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
            // postavit ćemo exitTranziciju
            exitTransition = Fade()
        }
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        // inside your activity (if you did not enable transitions in your theme)

        bottomNavigation = findViewById(R.id.navigationView)
        bottomNavigation.setOnItemSelectedListener(mOnItemSelectedListener)
        bottomNavigation.selectedItemId = R.id.navigation_favorites
        val favoritesFragment = FavoriteMoviesFragment.newInstance()
        openFragment(favoritesFragment)
        if (intent?.action == Intent.ACTION_SEND && intent?.type == "text/plain")
            handleSendText(intent)

        Intent(this, LatestMovieService::class.java).also {
//Različito pokretanje u ovisnosti od verzije
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(it)
                return
            }
            startService(it)
        }

    }


    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(br, filter)
    }

    override fun onPause() {
        unregisterReceiver(br)
        super.onPause()
    }

    private fun handleSendText(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            bottomNavigation.selectedItemId= R.id.navigation_search
            val searchFragment = SearchFragment.newInstance(it)
            openFragment(searchFragment)
        }
    }

}