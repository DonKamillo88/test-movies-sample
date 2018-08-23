package com.kkk.movies.ui.movies

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kkk.movies.R
import com.kkk.movies.data.model.Movie
import com.kkk.movies.utils.CACHE_ITEMS
import kotlinx.android.synthetic.main.fragment_movies.*

/**
 * @author DonKamillo on 22.08.2018.
 */
class MoviesFragment : Fragment(), MoviesMVP.View {

    val adapter = MoviesAdapter(arrayListOf())
    private val moviesPresenter: MoviesMVP.Presenter<MoviesMVP.View> = MoviesPresenter()

    companion object {
        val TAG = MoviesFragment::class.java.simpleName
        fun newInstance() = MoviesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()
        createSearchViewMenuItem()

        moviesPresenter.initPresenter(this, context?.cacheDir)
    }

    override fun onShowMovies(data: List<Movie>) {
        adapter.updateData(data)
    }

    private fun init() {
        movies.layoutManager = GridLayoutManager(context, resources.getInteger(R.integer.movies_columns))
        movies.hasFixedSize()
        movies.adapter = adapter
        movies.setItemViewCacheSize(CACHE_ITEMS)
    }

    private fun createSearchViewMenuItem() {
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
        search_view.setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))
    }

}