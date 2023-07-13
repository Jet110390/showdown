package com.example.showdown.data.local.daos

import androidx.room.*
import com.example.showdown.data.local.entities.FavoritePokemon
import com.example.showdown.data.local.entities.Pokemon
import com.example.showdown.data.remote.models.IdOption

@Dao
interface PokemonDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Upsert
    suspend fun upsertPokemonList(pokemon: List<Pokemon>)

    @Insert
    suspend fun addPokemonToFavs(pokemon: FavoritePokemon)

    @Query("SELECT * FROM pokemon")
    suspend fun getAllPokemon(): List<Pokemon>

    @Query("SELECT * FROM pokemon ORDER BY name ASC")
    suspend fun getAllPokemonInAlphabeticalOrder(): List<Pokemon>

    @Query("SELECT * FROM pokemon ORDER BY name DESC")
    suspend fun getAllPokemonInReverseAlphabeticalOrder(): List<Pokemon>

    @Query("SELECT * FROM pokemon ORDER BY id DESC")
    suspend fun getAllPokemonInReverseNumericalOrder(): List<Pokemon>

    @Query("SELECT count(*) FROM pokemon")
    suspend fun getPokemonCount(): Int

    @Query("SELECT * FROM pokemon WHERE speciesNumber IS :species")
    suspend fun getFavPokemonPokedexEntry(species: Int): Pokemon

    @Query("SELECT * FROM pokemon WHERE name IS :name")
    suspend fun getPokemonByName(name: String): Pokemon

    @Query("SELECT id, name, image FROM pokemon WHERE id IS :id")
    suspend fun getIdGameOption(id: Int): IdOption

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