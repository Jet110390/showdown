package com.example.showdown.data.remote.models.stats


import androidx.room.Ignore
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokeInfoDto(
    @Json(name = "abilities")
    @Ignore
    val abilities: List<Ability>,
    @Json(name = "base_experience")
    @Ignore
    val baseExperience: Int?,
    @Json(name = "forms")
    @Ignore
    val forms: List<Form>,
    @Json(name = "game_indices")
    val gameIndices: List<GameIndice>?,
    @Json(name = "height")
    val height: Int,
    @Json(name = "held_items")
    @Ignore
    val heldItems: List<HeldItem>?,
    @Json(name = "id")
    val id: Int,
    @Json(name = "is_default")
    @Ignore
    val isDefault: Boolean,
    @Json(name = "location_area_encounters")
    @Ignore
    val locationAreaEncounters: String,
    @Json(name = "moves")
    val moves: List<Move>?,
    @Json(name = "name")
    val name: String,
    @Json(name = "order")
    @Ignore
    val order: Int,
    @Json(name = "past_types")
    @Ignore
    val pastTypes: List<PastType>,
    @Json(name = "species")
    val species: Species,
    @Json(name = "sprites")
    val sprites: Sprites?,
    @Json(name = "stats")
    val stats: List<Stat>,
    @Json(name = "types")
    val types: List<TypeXX?>,
    @Json(name = "weight")
    val weight: Int
)