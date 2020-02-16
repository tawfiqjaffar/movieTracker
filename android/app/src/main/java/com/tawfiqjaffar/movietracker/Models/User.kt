package com.tawfiqjaffar.movietracker.Models

import com.google.gson.annotations.SerializedName

data class User(
    val favorites: List<String>,
    @SerializedName("_id") val id: String,
    val username: String
)