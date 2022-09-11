package com.example.showdown.data.remote.models.stats


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenerationIii(
    @Json(name = "emerald")
    val emerald: Emerald?,
    @Json(name = "firered-leafgreen")
    val fireredLeafgreen: FireredLeafgreen?,
    @Json(name = "ruby-sapphire")
    val rubySapphire: RubySapphire?
)