package com.kkk.movies.di

import android.content.Context
import com.kkk.movies.data.remote.MoviesRepository
import com.kkk.movies.ui.MyApplication
import com.kkk.movies.ui.movies.MoviesMVP
import com.kkk.movies.ui.movies.MoviesPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author DonKamillo on 23.08.2018.
 */
@Module
open class AppModule(private val app: MyApplication) {

    @Provides
    @Singleton
    fun provideApp(): MyApplication = app

    @Provides
    @Singleton
    fun provideMoviesPresenter(moviesRepository: MoviesRepository): MoviesMVP.Presenter = MoviesPresenter(moviesRepository)


}
