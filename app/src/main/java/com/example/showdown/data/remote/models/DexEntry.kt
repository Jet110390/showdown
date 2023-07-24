package com.example.showdown.data.remote.models

import com.example.showdown.data.local.entities.FavoritePokemon
import com.example.showdown.data.local.entities.Pokemon

data class DexEntry(
    val id: Int,
    val name: String,
    val image: String,
    val type1: String?,
    val type2: String? =" " ,
    val height: Int,
    val weight: Int,
//    val moves: List<String>,
//    val description: String,
    val hp: Int,
    val atk: Int,
    val def: Int,
    val spAtk: Int,
    val spDef: Int,
    val spd: Int,
//    val title: String,
//    val preEvo: String?
    val species: SpeciesEntry,
    val officialImg: String?,
//    val shinyImg: String?


){
    fun toPokemon(): Pokemon {
        return Pokemon(
            id = id,
            name = name,
            image = image,
            type1 = type1,
            type2 = type2,
            height = height,
            weight = weight,
//            moves = moves,
            description = species.description,
            hp = hp,
            atk = atk,
            def = def,
            spAtk = spAtk,
            spDef = spDef,
            spd = spd,
            title = species.title,
            preEvo = species.preEvo,
            officialImg = officialImg,
            speciesNumber = species.speciesNumber,
            speciesName = species.speciesName,
            variantAmount = species.varietyAmount
//            shinyImg = shinyImg


        )
    }
    fun toFavPokemon(): FavoritePokemon {
        return FavoritePokemon(
            id = id,
            name = name,
            image = image,
            type1 = type1,
            type2 = type2,
            height = height,
            weight = weight,
//            moves = moves,
//            description = species.description,
//            hp = hp,
//            atk = atk,
//            def = def,
//            spAtk = spAtk,
//            spDef = spDef,
//            spd = spd,
//            title = species.title,
//            preEvo = species.preEvo,
            officialImg = officialImg,
            speciesNumber = species.speciesNumber,
            speciesName = species.speciesName
        )
    }
}

