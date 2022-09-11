package com.example.showdown.data.remote.models.details


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Name(
    @Json(name = "language")
    val language: LanguageXX,
    @Json(name = "name")
    val name: String
)