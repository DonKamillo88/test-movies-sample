package com.kkk.movies.utils

import com.kkk.movies.data.model.Movie

/**
 * @author DonKamillo on 23.08.2018.
 */

fun movieSearchFilter(movie: Movie, filterString: String): Boolean {
    return movie.title.toLowerCase().contains(filterString) || movie.genre.toLowerCase().contains(filterString)
}