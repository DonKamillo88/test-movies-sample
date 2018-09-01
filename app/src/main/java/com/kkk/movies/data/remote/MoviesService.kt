package com.kkk.movies.data.remote

import com.kkk.movies.annotations.DebugOpenClass
import com.kkk.movies.data.model.MoviesData
import io.reactivex.Observable
import retrofit2.Response

/**
 * @author DonKamillo on 31.08.2018.
 */
@DebugOpenClass
class MoviesService(private val moviesApi: MoviesApi) {

    fun getMovies(): Observable<Response<MoviesData>> {
        return moviesApi.getMoviesObservable()
    }
}