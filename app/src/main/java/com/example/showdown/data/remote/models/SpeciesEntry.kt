package com.example.showdown.data.remote.models

data class SpeciesEntry(
    val description: String?,
    val title: String?,
    val preEvo: String?,
    val varieties: MutableList<VarietyEntry>,
    val speciesNumber: Int

)
