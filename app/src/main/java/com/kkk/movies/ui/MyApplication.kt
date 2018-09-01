package com.kkk.movies.ui

import android.app.Application
import com.kkk.movies.di.AppComponent
import com.kkk.movies.di.AppModule
import com.kkk.movies.di.DaggerAppComponent

/**
 * @author DonKamillo on 22.08.2018.
 */
class MyApplication : Application() {

    companion object {
        //platformStatic allow access it from java code
        @JvmStatic
        lateinit var appComponent: AppComponent
    }


    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()

        appComponent.inject(this)
    }
}