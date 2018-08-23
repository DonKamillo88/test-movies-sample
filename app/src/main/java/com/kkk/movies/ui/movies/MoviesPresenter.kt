package com.kkk.movies.ui.movies

import android.util.Log
import com.kkk.movies.data.model.MoviesData
import com.kkk.movies.data.remote.MoviesService
import io.reactivex.observers.DisposableObserver
import retrofit2.Response

/**
 * @author DonKamillo on 22.08.2018.
 */
class MoviesPresenter : MoviesMVP.Presenter {

    lateinit var moviesService: MoviesService
    lateinit var view: MoviesMVP.View

    override fun initPresenter(view: MoviesMVP.View, moviesService: MoviesService) {
        this.view = view
        this.moviesService = moviesService

        loadMovies()
    }

    private fun loadMovies() {

        val handler = object : DisposableObserver<Response<MoviesData>>() {
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
                Log.d("Network", e.localizedMessage)
            }
        }

        moviesService.getMovies(handler)

    }

}