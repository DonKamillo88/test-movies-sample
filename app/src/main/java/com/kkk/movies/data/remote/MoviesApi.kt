package com.kkk.movies.data.remote

import com.kkk.movies.annotations.DebugOpenClass
import com.kkk.movies.data.model.MoviesData
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

/**
 * @author DonKamillo on 22.08.2018.
 */
@DebugOpenClass
interface MoviesApi {

    // https://movies-sample.herokuapp.com/api/movies
    @GET("/api/movies")
    fun getMoviesObservable(): Observable<Response<MoviesData>>

}