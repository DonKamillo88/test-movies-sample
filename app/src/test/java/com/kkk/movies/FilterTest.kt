package com.kkk.movies

import com.kkk.movies.data.model.Movie
import com.kkk.movies.utils.movieSearchFilter
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class FilterTest {
    private val movie = Movie()

    @Before
    fun setUp() {
        movie.id = 38022
        movie.genre = "Sci-Fi"
        movie.posterUrl = "https://raw.githubusercontent.com/cesarferreira/sample-data/master/public/posters/038022.jpg"
        movie.title = "The Host"
        movie.year = 2013
    }

    @Test
    fun correctTitle() {
        assertTrue(movieSearchFilter(movie, "The Host"))
        assertTrue(movieSearchFilter(movie, "THE Host"))
        assertTrue(movieSearchFilter(movie, "the host"))
    }

    @Test
    fun incorrectTitle() {
        assertFalse(movieSearchFilter(movie, "Abc"))
        assertFalse(movieSearchFilter(movie, "The Host2"))
    }

    @Test
    fun correctTitlePart() {
        assertTrue(movieSearchFilter(movie, "The"))
        assertTrue(movieSearchFilter(movie, "The Hos"))
        assertTrue(movieSearchFilter(movie, "the"))
        assertTrue(movieSearchFilter(movie, " "))
        assertTrue(movieSearchFilter(movie, "ost"))
    }


    @Test
    fun correctGenre() {
        assertTrue(movieSearchFilter(movie, "Sci-Fi"))
        assertTrue(movieSearchFilter(movie, "sci-FI"))
    }

    @Test
    fun incorrectGenre() {
        assertFalse(movieSearchFilter(movie, "Abc"))
        assertFalse(movieSearchFilter(movie, "Sci-Fi2"))
    }

    @Test
    fun correctGenrePart() {
        assertTrue(movieSearchFilter(movie, "Sci"))
        assertTrue(movieSearchFilter(movie, "-"))
        assertTrue(movieSearchFilter(movie, "-fi"))
        assertTrue(movieSearchFilter(movie, "i"))
    }
}
