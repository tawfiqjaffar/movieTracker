package com.tawfiqjaffar.movietracker.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Movie(
    val id: Int,
    val title: String,
    @SerializedName("poster_path") val posterPath: String,
    val overview: String
) : Serializable