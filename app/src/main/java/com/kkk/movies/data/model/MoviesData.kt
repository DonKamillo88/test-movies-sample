package com.kkk.movies.data.model

import com.google.gson.annotations.SerializedName

/**
 * @author DonKamillo on 22.08.2018.
 */
data class MoviesData(@SerializedName("data") var data: List<Movie> = arrayListOf())