package com.kkk.movies

import com.kkk.movies.data.model.Movie
import com.kkk.movies.data.model.MoviesData
import com.kkk.movies.data.remote.MoviesRepository
import com.kkk.movies.ui.movies.MoviesMVP
import com.kkk.movies.ui.movies.MoviesPresenter
import io.reactivex.observers.DisposableObserver
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class MoviePresenterTest {

    private lateinit var SUT: MoviesMVP.Presenter
    private var moviesService = Mockito.mock(MoviesRepository::class.java)
    private var mView: MoviesMVP.View = Mockito.mock(MoviesMVP.View::class.java)

    @Captor
    private lateinit var mCaptor: ArgumentCaptor<DisposableObserver<Response<MoviesData>>>


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        SUT = MoviesPresenter(moviesService)
    }

    @Test
    fun shouldShowMoviesWhenStartAndServiceCallSuccess() {
        SUT.initPresenter(mView)
        SUT.loadMovies()

        Mockito.verify(moviesService, Mockito.times(1)).getMovies(any())
        Mockito.verify(moviesService).getMovies(capture(mCaptor))

        mCaptor.value.onNext(getMovieData())

        Mockito.verify(mView, Mockito.times(1)).onShowMovies(any())
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

        val moviesData = MoviesData()
        moviesData.data = listOf(movie1, movie2)

        return Response.success(moviesData)
    }


    /**
     * Returns Mockito.any() as nullable type to avoid java.lang.IllegalStateException when
     * null is returned.
     */
    fun <T> any(): T = Mockito.any<T>()


    /**
     * Returns ArgumentCaptor.capture() as nullable type to avoid java.lang.IllegalStateException
     * when null is returned.
     */
    fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()

}