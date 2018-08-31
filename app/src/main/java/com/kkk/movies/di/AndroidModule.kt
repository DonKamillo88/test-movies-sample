package com.kkk.movies.di

import android.content.Context
import com.kkk.movies.data.remote.MoviesApi
import com.kkk.movies.ui.movies.MoviesMVP
import com.kkk.movies.ui.movies.MoviesPresenter
import com.kkk.movies.utils.CACHE_MAX_AGE
import com.kkk.movies.utils.CACHE_SIZE
import com.kkk.movies.utils.MOVIES_API_URL
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author DonKamillo on 23.08.2018.
 */
@Module
open class AndroidModule(private val application: Context) {

    @Provides
    @Singleton
    @ForApplication
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun provideMoviesPresenter(): MoviesMVP.Presenter = MoviesPresenter()

    @Provides
    internal open fun provideMovies(converterFactory: Converter.Factory, callAdapterFactory: CallAdapter.Factory, httpClient: OkHttpClient): MoviesApi {
        return Retrofit.Builder()
                .baseUrl(MOVIES_API_URL)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .client(httpClient)
                .build()
                .create(MoviesApi::class.java)
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        // Create a cache object
        val httpCacheDirectory = File(application.cacheDir, "http-cache")
        val cache = Cache(httpCacheDirectory, CACHE_SIZE)

        // create a network cache interceptor, setting the max age
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

        return OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(networkCacheInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()
    }


    @Provides
    @Singleton
    fun provideGsonConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRxJavaCallAdapterFactory(): CallAdapter.Factory {
        return RxJava2CallAdapterFactory.create()
    }


}
