package com.example.showdown.data.local.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.showdown.data.local.daos.FavPokemonDao
import com.example.showdown.data.local.daos.PokemonDao
import com.example.showdown.data.local.daos.VariantDao
import com.example.showdown.data.local.entities.FavoritePokemon
import com.example.showdown.data.local.entities.Pokemon
import com.example.showdown.data.local.entities.Variant

@Database(entities = [Pokemon::class,FavoritePokemon::class,Variant::class], version = 1, exportSchema = true)
abstract class PokemonDatabase : RoomDatabase(){

    abstract fun getPokemonDao(): PokemonDao
    abstract fun getFavPokemonDao(): FavPokemonDao
    abstract fun getVariantDao(): VariantDao
}
