package com.kkk.movies.ui.movies

import com.kkk.movies.data.model.Movie

/**
 * @author DonKamillo on 22.08.2018.
 */
interface MoviesMVP {

    interface View {
        fun onShowMovies(data: List<Movie>)
        fun onShowProgressBar(isShow: Boolean)
    }

    interface Presenter {
        fun initPresenter(view: MoviesMVP.View)
        fun loadMovies()
        fun clearAll()
    }
}