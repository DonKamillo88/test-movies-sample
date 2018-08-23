package com.kkk.movies.data.remote

import com.kkk.movies.data.model.MoviesData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.jetbrains.annotations.NotNull
import retrofit2.Response
import java.io.File

class MoviesService(val cacheDir: File?) {
    fun getMovies(@NotNull handler: DisposableObserver<Response<MoviesData>>) {
        val moviesApi = Repository(cacheDir).getMoviesApi()

        moviesApi.getMoviesObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(handler)
    }
}