package com.example.showdown.data.local.daos

import androidx.room.*
import com.example.showdown.data.local.entities.FavoritePokemon
import com.example.showdown.data.local.entities.Pokemon

@Dao
interface FavPokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPokemonToFavs(pokemon: FavoritePokemon)

    @Delete
    suspend fun removePokemonFromFavs(pokemon: FavoritePokemon)

    @Query("SELECT * FROM favoritePokemon")
    suspend fun getAllFavs(): List<FavoritePokemon>

    @Query("SELECT count(*) FROM favoritePokemon")
    suspend fun getFavPokemonCount(): Int

    @Query("SELECT speciesNumber FROM favoritePokemon WHERE speciesNumber IS :species ")
    suspend fun getFavDex(species: Int): Int
}