package com.kkk.movies.di

import com.kkk.movies.annotations.DebugOpenClass
import com.kkk.movies.data.remote.MoviesApi
import com.kkk.movies.data.remote.MoviesRepository
import com.kkk.movies.data.remote.MoviesService
import com.kkk.movies.ui.MyApplication
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
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author DonKamillo on 31.08.2018.
 */
@DebugOpenClass
@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideRepository(moviesApi: MoviesApi): MoviesService {
        return MoviesService(moviesApi)
    }

    @Provides
    @Singleton
    fun provideMoviesService(moviesService: MoviesService): MoviesRepository {
        return MoviesRepository(moviesService)
    }

    @Provides
    internal fun provideMoviesApi(converterFactory: Converter.Factory, callAdapterFactory: CallAdapter.Factory, httpClient: OkHttpClient): MoviesApi {
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
    fun provideGsonConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRxJavaCallAdapterFactory(): CallAdapter.Factory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(cache: Cache, @Named("networkCacheInterceptor") networkCacheInterceptor: Interceptor, @Named("loggingInterceptor") loggingInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(networkCacheInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()
    }

    @Provides
    @Singleton
    fun provideCache(app: MyApplication): Cache {
        val httpCacheDirectory = File(app.cacheDir, "http-cache")
        return Cache(httpCacheDirectory, CACHE_SIZE)
    }

    @Provides
    @Singleton
    @Named("loggingInterceptor")
    fun provideLoggingInterceptor(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return loggingInterceptor
    }

    @Provides
    @Singleton
    @Named("networkCacheInterceptor")
    fun provideNetworkCacheInterceptor(): Interceptor {
        // create a network cache interceptor, setting the max age
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())

            val cacheControl = CacheControl.Builder()
                    .maxAge(CACHE_MAX_AGE, TimeUnit.MINUTES)
                    .build()

            response.newBuilder()
                    .header("Cache-Control", cacheControl.toString())
                    .build()
        }
    }

}