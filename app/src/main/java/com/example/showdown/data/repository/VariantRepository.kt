package com.example.showdown.data.repository

import android.util.Log
import com.example.showdown.data.local.daos.VariantDao
import com.example.showdown.data.local.entities.Pokemon
import com.example.showdown.data.local.entities.Variant
import javax.inject.Inject

class VariantRepository @Inject constructor(
    private val variantDao: VariantDao
    ){
    suspend fun addToFavs(pokemon: Variant): Int {
        val favoritePokemon = pokemon.toFavPokemon()
        variantDao.addVariantToFavs(favoritePokemon)
        Log.d("favId","${favoritePokemon}")
        return favoritePokemon.id
    }
}