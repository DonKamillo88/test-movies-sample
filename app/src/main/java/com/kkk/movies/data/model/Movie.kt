package com.kkk.movies.data.model

import com.google.gson.annotations.SerializedName

/**
 * @author DonKamillo on 22.08.2018.
 */
data class Movie(@SerializedName("id") var id : Long = 0,
            @SerializedName("title") var title : String = "",
            @SerializedName("year") var year : Int = 0,
            @SerializedName("genre") var genre : String = "",
            @SerializedName("poster") var posterUrl : String = "")