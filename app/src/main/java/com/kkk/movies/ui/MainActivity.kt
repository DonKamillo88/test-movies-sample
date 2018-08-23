package com.kkk.movies.ui

import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.kkk.movies.R
import com.kkk.movies.ui.movies.MoviesFragment
import com.kkk.movies.ui.movies.MoviesMVP
import com.kkk.movies.ui.movies.MoviesPresenter
import javax.inject.Inject


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(MoviesFragment.newInstance())
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }


}
