package com.kkk.movies.ui

import android.app.Application
import com.kkk.movies.di.AndroidModule
import com.kkk.movies.di.ApplicationComponent
import com.kkk.movies.di.DaggerApplicationComponent

/**
 * @author DonKamillo on 22.08.2018.
 */
class MyApplication : Application() {

    companion object {
        //platformStatic allow access it from java code
        @JvmStatic
        lateinit var graph: ApplicationComponent
    }


    override fun onCreate() {
        super.onCreate()
        graph = DaggerApplicationComponent.builder().androidModule(AndroidModule(this)).build()
        graph.inject(this)
    }
}