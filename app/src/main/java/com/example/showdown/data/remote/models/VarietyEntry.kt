package com.example.showdown.data.remote.models

import com.example.showdown.data.local.entities.Variant

data class VarietyEntry(
    val id: Int,
    val name: String,
    val image: String?,
    val type1: String?,
    val type2: String? =" " ,
    val height: Int,
    val weight: Int,
    val species: Int,
    val officialImg: String?,
//    val shinyImg: String?
){
    fun toVariant(): Variant {
        return Variant(
            id = id,
            name = name,
            image = image,
            type1 = type1,
            type2 = type2 ,
            height = height,
            weight = weight,
            species = species,
            officialImg = officialImg,
//            shinyImg = shinyImg
        )
    }
}
