package com.example.showdown.di

import com.example.showdown.data.local.daos.FavPokemonDao
import com.example.showdown.data.local.daos.PokemonDao
import com.example.showdown.data.local.daos.VariantDao
import com.example.showdown.data.remote.services.ShowdownService
import com.example.showdown.data.repository.FavPokemonRepository
import com.example.showdown.data.repository.PokemonInfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesPokemonRepository(
        pokemonService: ShowdownService,
        pokemonDao: PokemonDao,
        favPokemonDao: FavPokemonDao,
        variantDao: VariantDao
    ): PokemonInfoRepository {
        return PokemonInfoRepository(pokemonService, pokemonDao, favPokemonDao, variantDao)
    }

    @Singleton
    @Provides
    fun providesFavPokemonRepository(
        favPokemonDao: FavPokemonDao,
        pokemonDao: PokemonDao
    ): FavPokemonRepository {
        return FavPokemonRepository(favPokemonDao,pokemonDao)
    }

//    @Singleton
//    @Provides
//    fun providesFavPokemonRepository(
//        favPokemonDao: FavPokemonDao,
////        variantDao: VariantDao
//    ): FavPokemonRepository {
//        return FavPokemonRepository(favPokemonDao)
//    }
}