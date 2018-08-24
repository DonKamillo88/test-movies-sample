package com.kkk.movies

import android.content.Context
import com.kkk.movies.data.remote.MoviesApi
import com.kkk.movies.di.AndroidModule
import dagger.Provides
import okhttp3.OkHttpClient
import org.mockito.Mockito
import retrofit2.CallAdapter
import retrofit2.Converter

/**
 * @author DonKamillo on 23.08.2018.
 */
class AndroidTestModule(private val application: Context) : AndroidModule(application) {

    @Provides
    override fun provideMovies(converterFactory: Converter.Factory, callAdapterFactory: CallAdapter.Factory, httpClient: OkHttpClient): MoviesApi {
        return Mockito.mock(MoviesApi::class.java)
    }
}