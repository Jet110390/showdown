package com.example.showdown.data.remote.models.details


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Genera(
    @Json(name = "genus")
    val genus: String,
    @Json(name = "language")
    val language: LanguageX
)