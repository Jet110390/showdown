package com.example.showdown.data.remote.models.urls

import androidx.room.Ignore
import com.example.showdown.data.remote.models.Urls
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseDto(
    @Json(name = "count")
    @Ignore
    val count: Int,
    @Json(name = "next")
    @Ignore
    val next: String?,
    @Json(name = "previous")
    @Ignore
    val previous: String?,
    @Json(name = "results")
    val results: List<Urls>
)


