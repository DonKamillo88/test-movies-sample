package com.kkk.movies.ui


import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.kkk.movies.R
import com.kkk.movies.data.model.Movie
import com.kkk.movies.data.model.MoviesData
import com.kkk.movies.data.remote.MoviesService
import com.kkk.movies.di.AppModule
import com.kkk.movies.di.DaggerTestAppComponent
import com.kkk.movies.di.TestApiModule
import com.kkk.movies.di.TestAppComponent
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import retrofit2.Response
import javax.inject.Inject


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val mActivityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java, false, false)

    @Inject
    lateinit var moviesService: MoviesService

    private lateinit var testAppComponent: TestAppComponent

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        val app = InstrumentationRegistry.getTargetContext().applicationContext as MyApplication
        testAppComponent = DaggerTestAppComponent.builder()
                .appModule(AppModule(app))
                .apiModule(TestApiModule())
                .build()
        MyApplication.appComponent = testAppComponent
        testAppComponent.inject(this)
    }

    @Test
    fun displayProgressBarWhenLaunchActivity() {
        // given
        whenever(moviesService.getMovies()).thenReturn(Observable.empty())

        // when
        mActivityTestRule.launchActivity(null)

        // then
        Espresso.onView(ViewMatchers.withId(R.id.progressbar)).check(matches(isDisplayed()))
    }

    @Test
    fun displayMoviesListWhenGetSuccessfulData() {
        // given
        whenever(moviesService.getMovies()).thenReturn(Observable.just(getMovieData()))

        // when
        mActivityTestRule.launchActivity(null)

        // then
        Espresso.onView(ViewMatchers.withId(R.id.movies)).check(matches(isDisplayed()))
    }

    @Test
    fun displaySpecificMovieWhenGetSuccessfulData() {
        // given
        whenever(moviesService.getMovies()).thenReturn(Observable.just(getMovieData()))

        // when
        mActivityTestRule.launchActivity(null)

        // then
        onView(withText("Sci-Fi")).check(matches(isDisplayed()));

    }

    private fun getMovieData(): Response<MoviesData> {

        val movie1 = Movie()
        movie1.id = 38022
        movie1.genre = "Sci-Fi"
        movie1.posterUrl = "https://raw.githubusercontent.com/cesarferreira/sample-data/master/public/posters/038022.jpg"
        movie1.title = "The Host"
        movie1.year = 2013

        val movie2 = Movie()
        movie2.id = 38023
        movie2.genre = "Fantasy"
        movie2.posterUrl = "https://raw.githubusercontent.com/cesarferreira/sample-data/master/public/posters/038023.jpg"
        movie2.title = "Conan the Barbarian"
        movie2.year = 2011

        val movie3 = Movie()
        movie3.id = 46232
        movie3.genre = "Fantasy"
        movie3.posterUrl = "https://image.tmdb.org/t/p/w370_and_h556_bestv2/kOVEVeg59E0wsnXmF9nrh6OmWII.jpg"
        movie3.title = "Star Wars: The Last Jedi"
        movie3.year = 2017
        val moviesData = MoviesData()
        moviesData.data = listOf(movie1, movie2, movie3)

        return Response.success(moviesData)
    }
}