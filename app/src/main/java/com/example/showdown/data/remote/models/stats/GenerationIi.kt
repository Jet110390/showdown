package com.example.showdown.data.remote.models.stats


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenerationIi(
    @Json(name = "crystal")
    val crystal: Crystal?,
    @Json(name = "gold")
    val gold: Gold?,
    @Json(name = "silver")
    val silver: Silver?
)