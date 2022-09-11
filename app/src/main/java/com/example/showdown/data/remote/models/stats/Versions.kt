package com.example.showdown.data.remote.models.stats


import com.example.showdown.data.remote.models.stats.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Versions(
    @Json(name = "generation-i")
    val generationI: GenerationI?,
    @Json(name = "generation-ii")
    val generationIi: GenerationIi?,
    @Json(name = "generation-iii")
    val generationIii: GenerationIii?,
    @Json(name = "generation-iv")
    val generationIv: GenerationIv?,
    @Json(name = "generation-v")
    val generationV: GenerationV?,
    @Json(name = "generation-vi")
    val generationVi: GenerationVi?,
    @Json(name = "generation-vii")
    val generationVii: GenerationVii?,
    @Json(name = "generation-viii")
    val generationViii: GenerationViii?
)