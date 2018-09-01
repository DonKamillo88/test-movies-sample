package com.kkk.movies.di

import com.kkk.movies.ui.MainActivityTest
import dagger.Component
import javax.inject.Singleton

/**
 * @author DonKamillo on 31.08.2018.
 */
@Singleton
@Component(modules = [AppModule::class, ApiModule::class])
interface TestAppComponent : AppComponent {
    fun inject(test: MainActivityTest)
}
