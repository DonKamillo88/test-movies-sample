package com.kkk.movies.ui.movies

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kkk.movies.R
import com.kkk.movies.data.model.Movie
import com.kkk.movies.ui.MoviesAdapter
import kotlinx.android.synthetic.main.fragment_movies.*

/**
 * @author DonKamillo on 22.08.2018.
 */
class MoviesFragment : Fragment(), MoviesMVP.View {

    val adapter = MoviesAdapter(arrayListOf())
    val moviesPresenter: MoviesMVP.Presenter<MoviesMVP.View> = MoviesPresenter()

    companion object {
        val TAG = MoviesFragment::class.java.simpleName
        fun newInstance() = MoviesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_movies, container, false)
        Log.e(TAG, " onCreateView")

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()
        moviesPresenter.initPresenter(this, context?.cacheDir)
    }

    override fun onShowMovies(data: List<Movie>) {
        Log.e(TAG, " onShowMovies")
        adapter.updateData(data)
    }

    private fun init() {
        movies.layoutManager = GridLayoutManager(context, 3)
        movies.hasFixedSize()
        movies.adapter = adapter
        movies.setItemViewCacheSize(20);

    }

}