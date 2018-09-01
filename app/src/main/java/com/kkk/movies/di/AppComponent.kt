package com.kkk.movies.di

import com.kkk.movies.ui.MainActivity
import com.kkk.movies.ui.MyApplication
import com.kkk.movies.ui.movies.MoviesFragment
import dagger.Component
import javax.inject.Singleton

/**
 * @author DonKamillo on 23.08.2018.
 */
@Singleton
@Component(modules = [AppModule::class, ApiModule::class])
interface AppComponent {
    fun inject(app: MyApplication)
    fun inject(moviesFragment: MoviesFragment)
}
