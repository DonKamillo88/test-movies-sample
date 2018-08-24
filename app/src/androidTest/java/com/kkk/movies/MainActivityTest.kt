package com.kkk.movies


import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.kkk.movies.di.DaggerApplicationComponent
import com.kkk.movies.ui.MainActivity
import com.kkk.movies.ui.MyApplication
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val mActivityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java, false, false)

    @Before
    fun init() {
        val appContext = InstrumentationRegistry.getTargetContext()

        //getting the application class
        val myApp = InstrumentationRegistry
                .getInstrumentation()
                .targetContext
                .applicationContext as MyApplication

        //building the app component with the mocked module
        val mockedComponent = DaggerApplicationComponent.builder()
                .androidModule(AndroidTestModule(appContext)).build()
        mockedComponent.inject(myApp)

    }


    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.kkk.movies", appContext.packageName)
    }

    @Test
    fun displayMovies() {
    }

    @Test
    fun displayMoviesSearchByTitle() {
    }

    @Test
    fun displayMoviesSearchByGenre() {
    }

}