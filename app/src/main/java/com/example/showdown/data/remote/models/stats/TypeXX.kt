package com.example.showdown.data.remote.models.stats


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TypeXX(
    @Json(name = "slot")
    val slot: Int,
    @Json(name = "type")
    val type: TypeXXX
)