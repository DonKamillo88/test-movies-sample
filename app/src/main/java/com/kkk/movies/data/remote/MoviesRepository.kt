package com.kkk.movies.data.remote

import com.kkk.movies.annotations.DebugOpenClass
import com.kkk.movies.data.model.MoviesData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.jetbrains.annotations.NotNull
import retrofit2.Response

@DebugOpenClass
class MoviesRepository(private val moviesService: MoviesService) {

    fun getMovies(@NotNull handler: DisposableObserver<Response<MoviesData>>) {
        moviesService.getMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(handler)
    }

}