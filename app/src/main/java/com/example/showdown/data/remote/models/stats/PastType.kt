package com.example.showdown.data.remote.models.stats


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PastType(
    @Json(name = "generation")
    val generation: Generation,
    @Json(name = "types")
    val types: List<Type>
)