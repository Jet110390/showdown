package com.example.showdown.di

import android.content.Context
import androidx.room.Room
import com.example.showdown.data.local.daos.FavPokemonDao
import com.example.showdown.data.local.daos.PokemonDao
import com.example.showdown.data.local.daos.VariantDao
import com.example.showdown.data.local.databases.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesPokemonDatabase(
        @ApplicationContext context: Context
    ): PokemonDatabase {
        return Room.databaseBuilder(
            context,
            PokemonDatabase :: class.java,
            "pokemon_database"
        ).build()
    }

    @Provides
    fun providesPokemonDao(database: PokemonDatabase): PokemonDao {
        return database.getPokemonDao()
    }

//    @Provides
//    @Singleton
//    fun providesFavoritePokemonDatabase(
//        @ApplicationContext context: Context
//    ): PokemonDatabase {
//        return Room.databaseBuilder(
//            context,
//            PokemonDatabase :: class.java,
//            "pokemon_database"
//        ).build()
//    }

    @Provides
    fun providesFavoritePokemonDao(database: PokemonDatabase): FavPokemonDao {
        return database.getFavPokemonDao()
    }

    @Provides
    fun providesVariantDao(database: PokemonDatabase): VariantDao {
        return database.getVariantDao()
    }
}