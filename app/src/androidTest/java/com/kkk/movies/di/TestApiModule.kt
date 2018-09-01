package com.kkk.movies.di

import com.kkk.movies.data.remote.MoviesApi
import com.kkk.movies.data.remote.MoviesService
import okhttp3.OkHttpClient
import org.mockito.Mockito
import retrofit2.CallAdapter
import retrofit2.Converter

/**
 * @author DonKamillo on 23.08.2018.
 */
class TestApiModule : ApiModule() {

    override fun provideRepository(moviesApi: MoviesApi): MoviesService {
        return Mockito.mock(MoviesService::class.java)
    }

    override fun provideMoviesApi(converterFactory: Converter.Factory, callAdapterFactory: CallAdapter.Factory, httpClient: OkHttpClient): MoviesApi {
        return Mockito.mock(MoviesApi::class.java)
    }
}