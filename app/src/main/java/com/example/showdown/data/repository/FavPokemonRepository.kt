package com.example.showdown.data.repository

import android.util.Log
import com.example.showdown.data.local.daos.FavPokemonDao
import com.example.showdown.data.local.daos.PokemonDao
import com.example.showdown.data.local.entities.FavoritePokemon
import com.example.showdown.data.local.entities.Pokemon
import javax.inject.Inject

class FavPokemonRepository @Inject constructor(
    private val favPokeDao: FavPokemonDao,
    private val pokemonDao: PokemonDao
) {
    suspend fun addToFavs(pokemon: Pokemon): Int {
           val favoritePokemon=pokemon.toFavPokemon()
        favPokeDao.addPokemonToFavs(favoritePokemon)
        Log.d("favId","${favoritePokemon}")
        return favoritePokemon.id
    }

    suspend fun deleteFromFavs(formerFav:FavoritePokemon): Int {
        favPokeDao.removePokemonFromFavs(formerFav)
        return formerFav.id
    }

    suspend fun getFavs(): List<FavoritePokemon> {
//        Log.d("favs","${favPokeDao.getAllFavs()}")
        return favPokeDao.getAllFavs()
    }

    suspend fun getDex(fav: FavoritePokemon): Pokemon{
        val speciesNum = favPokeDao.getFavDex(fav.speciesNumber!!)
        Log.d("dexEntry species","$speciesNum , ${fav.speciesNumber} $fav")
        val pokemon = pokemonDao.getFavPokemonPokedexEntry(speciesNum)
        return pokemon
    }


//    fun removeFromFavs(pokemon: FavoritePokemon): Int {
//        favPokeDao.removePokemonFromFavs(pokemon)
//        return pokemon.id
//    }
}