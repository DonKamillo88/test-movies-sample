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
@Component(modules = arrayOf(AndroidModule::class))
interface ApplicationComponent {
    fun inject(application: MyApplication)

    fun inject(mainActivity: MainActivity)
    fun inject(moviesFragment: MoviesFragment)
}
