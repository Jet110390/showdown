package com.example.showdown.data.remote.models

import androidx.room.Ignore
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Urls(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
    )

