package com.tawfiqjaffar.movietracker.Models

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("_id") val id: String,
    val name: String,
    val movieId: String,
    val content: String,
    val userId: String
)