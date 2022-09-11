package com.example.showdown.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.showdown.data.local.entities.FavoritePokemon
import com.example.showdown.data.local.entities.Pokemon
import com.example.showdown.data.local.entities.Variant

@Dao
interface VariantDao {
    @Insert
    suspend fun addVariant(variant: Variant)

    @Insert
    suspend fun addVariantToFavs(pokemon: FavoritePokemon)

    @Query("SELECT * FROM variant WHERE species Is :speciesId ")
    fun getVariants(speciesId: Int): List<Variant>
}