package com.example.showdown.data.remote.models.details


import androidx.room.Ignore
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpeciesInfoDto(
    @Json(name = "base_happiness")
    @Ignore
    val baseHappiness: Int?,
    @Json(name = "capture_rate")
    @Ignore
    val captureRate: Int?,
    @Json(name = "color")
    @Ignore
    val color: Color,
    @Json(name = "egg_groups")
    @Ignore
    val eggGroups: List<EggGroup?>?,
    @Json(name = "evolution_chain")
    @Ignore
    val evolutionChain: EvolutionChain?,
    @Json(name = "evolves_from_species")
    @Ignore
    val evolvesFromSpecies: PreEvo?,
    @Json(name = "flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntry>,
    @Json(name = "form_descriptions")
    @Ignore
    val formDescriptions: List<Any?>?,
    @Json(name = "forms_switchable")
    @Ignore
    val formsSwitchable: Boolean,
    @Json(name = "gender_rate")
    @Ignore
    val genderRate: Int?,
    @Json(name = "genera")
    @Ignore
    val genera: List<Genera>,
    @Json(name = "generation")
    @Ignore
    val generation: Generation,
    @Json(name = "growth_rate")
    @Ignore
    val growthRate: GrowthRate,
    @Json(name = "habitat")
    @Ignore
    val habitat: Any?,
    @Json(name = "has_gender_differences")
    @Ignore
    val hasGenderDifferences: Boolean,
    @Json(name = "hatch_counter")
    @Ignore
    val hatchCounter: Int?,
    @Json(name = "id")
    @Ignore
    val id: Int,
    @Json(name = "is_baby")
    @Ignore
    val isBaby: Boolean,
    @Json(name = "is_legendary")
    @Ignore
    val isLegendary: Boolean,
    @Json(name = "is_mythical")
    @Ignore
    val isMythical: Boolean,
    @Json(name = "name")
    @Ignore
    val name: String,
    @Json(name = "names")
    @Ignore
    val names: List<Name>,
    @Json(name = "order")
    @Ignore
    val order: Int,
    @Json(name = "pal_park_encounters")
    @Ignore
    val palParkEncounters: List<Any?>?,
    @Json(name = "pokedex_numbers")
    @Ignore
    val pokedexNumbers: List<PokedexNumber>?,
    @Json(name = "shape")
    @Ignore
    val shape: Shape?,
    @Json(name = "varieties")
    @Ignore
    val varieties: List<Variety>
)