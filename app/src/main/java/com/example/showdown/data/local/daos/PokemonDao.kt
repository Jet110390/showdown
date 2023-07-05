package com.example.showdown.data.local.daos

import androidx.room.*
import com.example.showdown.data.local.entities.FavoritePokemon
import com.example.showdown.data.local.entities.Pokemon
import kotlinx.coroutines.flow.Flow

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

    @Query("SELECT * FROM pokemon WHERE id >= 1 AND id <=151")
    fun getGenOne(): List<Pokemon>

    @Query("SELECT * FROM pokemon WHERE id >= 152 AND id <=251")
    fun getGenTwo(): List<Pokemon>

    @Query("SELECT * FROM pokemon WHERE id >= 252 AND id <=386")
    fun getGenThree(): List<Pokemon>

    @Query("SELECT * FROM pokemon WHERE id >= 387 AND id <=493")
    fun getGenFour(): List<Pokemon>

    @Query("SELECT * FROM pokemon WHERE id >= 494 AND id <=649")
    fun getGenFive(): List<Pokemon>

    @Query("SELECT * FROM pokemon WHERE id >= 650 AND id <=721")
    fun getGenSix(): List<Pokemon>

    @Query("SELECT * FROM pokemon WHERE id >= 722 AND id <=809")
    fun getGenSeven(): List<Pokemon>

    @Query("SELECT * FROM pokemon WHERE id >= 810 AND id <=905")
    fun getGenEight(): List<Pokemon>
    @Query("SELECT * FROM pokemon WHERE id >= 906 AND id <=1010")
    fun getGenNine(): List<Pokemon>

    @Query("SELECT * FROM pokemon")
    fun getFullDex(): List<Pokemon>

    @Query("SELECT officialImg FROM pokemon WHERE id IS :id")
    fun getOfficialImg(id: Int): String
}