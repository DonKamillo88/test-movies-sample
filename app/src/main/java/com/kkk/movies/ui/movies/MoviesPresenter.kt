package com.kkk.movies.ui.movies

import android.util.Log
import com.kkk.movies.data.model.MoviesData
import com.kkk.movies.data.remote.MoviesRepository
import io.reactivex.observers.DisposableObserver
import retrofit2.Response
import javax.inject.Inject

/**
 * @author DonKamillo on 22.08.2018.
 */
class MoviesPresenter @Inject constructor(private val moviesRepository: MoviesRepository) : MoviesMVP.Presenter {

    lateinit var view: MoviesMVP.View
    private lateinit var handler: DisposableObserver<Response<MoviesData>>

    override fun initPresenter(view: MoviesMVP.View) {
        this.view = view
    }

    override fun loadMovies() {
        view.onShowProgressBar(true)
        handler = getMoviesHandler()
        moviesRepository.getMovies(handler)
    }

    override fun clearAll() {
        handler.dispose()
    }

    private fun getMoviesHandler(): DisposableObserver<Response<MoviesData>> {

        return object : DisposableObserver<Response<MoviesData>>() {
            override fun onNext(response: Response<MoviesData>) {

                if (response.raw().cacheResponse() != null) {
                    Log.d("Network", "response came from cache")
                }

                if (response.raw().networkResponse() != null) {
                    Log.d("Network", "response came from server")
                }

                response.body()?.data?.let {
                    view.onShowProgressBar(false)
                    view.onShowMovies(it)
                }
            }

            override fun onComplete() {
                Log.d("Network", "onComplete")
            }

            override fun onError(e: Throwable) {
                Log.d("Network", e.localizedMessage)
            }
        }
    }
}