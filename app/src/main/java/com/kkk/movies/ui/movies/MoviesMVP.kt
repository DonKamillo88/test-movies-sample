package com.kkk.movies.ui.movies

import com.kkk.movies.data.model.Movie
import com.kkk.movies.data.remote.MoviesService

/**
 * @author DonKamillo on 22.08.2018.
 */
interface MoviesMVP {

    interface View {
        fun onShowMovies(data: List<Movie>)
    }

    interface Presenter<T> {
        fun initPresenter(view: T, moviesService: MoviesService)
    }
}