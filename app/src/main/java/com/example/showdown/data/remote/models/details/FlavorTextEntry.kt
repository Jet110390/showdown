package com.example.showdown.data.remote.models.details


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FlavorTextEntry(
    @Json(name = "flavor_text")
    val flavorText: String,
    @Json(name = "language")
    val language: Language,
    @Json(name = "version")
    val version: Version
)