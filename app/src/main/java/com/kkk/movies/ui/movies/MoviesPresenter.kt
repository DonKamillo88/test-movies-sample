package com.kkk.movies.ui.movies

import android.util.Log
import com.kkk.movies.data.model.MoviesData
import com.kkk.movies.data.remote.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.io.File

/**
 * @author DonKamillo on 22.08.2018.
 */
class MoviesPresenter : MoviesMVP.Presenter<MoviesMVP.View> {

    lateinit var view: MoviesMVP.View
    var cacheDir: File? = null

    override fun initPresenter(view: MoviesMVP.View, cacheDir: File?) {
        this.view = view
        this.cacheDir = cacheDir

        loadMovies()
    }

    private fun loadMovies() {
        val moviesApi = Repository(cacheDir).getMoviesApi()

        moviesApi.getMoviesObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Response<MoviesData>>() {
                    override fun onNext(response: Response<MoviesData>) {

                        if (response.raw().cacheResponse() != null) {
                            Log.d("Network", "response came from cache")
                        }

                        if (response.raw().networkResponse() != null) {
                            Log.d("Network", "response came from server")
                        }

                        response.body()?.data?.let { view.onShowMovies(it) }
                    }

                    override fun onComplete() {
                        Log.d("Network", "onComplete")
                    }

                    override fun onError(e: Throwable) {
                        Log.e("Network", e.localizedMessage)
                    }
                })
    }
}