package com.kkk.movies.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.kkk.movies.R
import com.kkk.movies.data.model.MoviesData
import com.kkk.movies.data.remote.MoviesApi
import com.kkk.movies.utils.CACHE_MAX_AGE
import com.kkk.movies.utils.CACHE_SIZE
import com.kkk.movies.utils.MOVIES_API_URL
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import android.support.v7.widget.GridLayoutManager



class MainActivity : AppCompatActivity() {
    val adapter = MoviesAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        loadMovies()
    }

    private fun init() {
        movies.layoutManager = GridLayoutManager(this, 3)
        movies.hasFixedSize()
        movies.adapter = adapter
//        movies.setItemViewCacheSize(20);

    }

    fun loadMovies() {

        // Create a cache object
        val httpCacheDirectory = File(cacheDir, "http-cache")
        val cache = Cache(httpCacheDirectory, CACHE_SIZE)

        // create a network cache interceptor, setting the max age to 1 minute
        val networkCacheInterceptor = Interceptor { chain ->
            val response = chain.proceed(chain.request())

            val cacheControl = CacheControl.Builder()
                    .maxAge(CACHE_MAX_AGE, TimeUnit.MINUTES)
                    .build()

            response.newBuilder()
                    .header("Cache-Control", cacheControl.toString())
                    .build()
        }

        // Create the logging interceptor
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY


        // Create the httpClient, configure it
        // with cache, network cache interceptor and logging interceptor
        val httpClient = OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(networkCacheInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()

        // Create the Retrofit with the httpClient
        val retrofit = Retrofit.Builder()
                .baseUrl(MOVIES_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build()

        // Build the moviesApi with Retrofit and do the network request
        val moviesApi = retrofit.create(MoviesApi::class.java)




        moviesApi.getMoviesObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Response<MoviesData>>() {
                    override fun onNext(response: Response<MoviesData>) {

                        if (response.raw().cacheResponse() != null) {
                            Log.e("Network", "response came from cache")
                        }

                        if (response.raw().networkResponse() != null) {
                            Log.e("Network", "response came from server")
                        }

                        response.body()?.data?.let { adapter.updateData(it) }
                    }

                    override fun onComplete() {
                        Log.e("Network", "onComplete")
                    }

                    override fun onError(e: Throwable) {
                        Log.e("Network", e.localizedMessage)
                    }
                })
    }
}
