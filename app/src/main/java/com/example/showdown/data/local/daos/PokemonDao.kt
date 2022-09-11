package com.example.showdown.data.local.daos

import androidx.room.*
import com.example.showdown.data.local.entities.FavoritePokemon
import com.example.showdown.data.local.entities.Pokemon

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: List<Pokemon>)

    @Insert
    suspend fun addPokemonToFavs(pokemon: FavoritePokemon)

    @Query("SELECT * FROM pokemon")
    suspend fun getAllPokemon(): List<Pokemon>

    @Query("SELECT count(*) FROM pokemon")
    suspend fun getPokemonCount(): Int

    @Query("SELECT * FROM pokemon WHERE speciesNumber IS :species")
    suspend fun getFavPokemonPokedexEntry(species: Int): Pokemon

//    @Query("SELECT * FROM pokemon WHERE name Is :name")
//    suspend fun getPokemon(): Pokemon

//    @Query("SELECT * FROM pokemon WHERE id >= 1 AND id <=151")
//    suspend fun getGenOne(): List<Pokemon>
//
//    @Query("SELECT * FROM pokemon WHERE id >= 152 AND id <=251")
//    suspend fun getGenTwo(): List<Pokemon>
//
//    @Query("SELECT * FROM pokemon WHERE id >= 252 AND id <=386")
//    suspend fun getGenThree(): List<Pokemon>
//
//    @Query("SELECT * FROM pokemon WHERE id >= 387 AND id <=493")
//    suspend fun getGenFour(): List<Pokemon>
//
//    @Query("SELECT * FROM pokemon WHERE id >= 494 AND id <=649")
//    suspend fun getGenFive(): List<Pokemon>
//
//    @Query("SELECT * FROM pokemon WHERE id >= 650 AND id <=721")
//    suspend fun getGenSix(): List<Pokemon>
//
//    @Query("SELECT * FROM pokemon WHERE id >= 722 AND id <=809")
//    suspend fun getGenSeven(): List<Pokemon>
//
//    @Query("SELECT * FROM pokemon WHERE id >= 810 AND id <=898")
//    suspend fun getGenEight(): List<Pokemon>

}