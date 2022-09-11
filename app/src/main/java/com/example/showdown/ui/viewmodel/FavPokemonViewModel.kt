package com.example.showdown.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.showdown.data.local.entities.FavoritePokemon
import com.example.showdown.data.local.entities.Pokemon
import com.example.showdown.data.remote.models.DexEntry
import com.example.showdown.data.remote.models.Urls
import com.example.showdown.data.repository.FavPokemonRepository
import com.example.showdown.data.repository.PokemonInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavPokemonViewModel @Inject constructor(
    private val favPokemonRepository: FavPokemonRepository
): ViewModel() {

    private var _favPokes: MutableLiveData<List<FavoritePokemon>?> = MutableLiveData()
    val favPokes: LiveData<List<FavoritePokemon>?> get() = _favPokes

    init{
        getFavs()
    }
    fun getFavs() {
        viewModelScope.launch (IO){
            val response = favPokemonRepository.getFavs()
            _favPokes.postValue(response)
        }
    }

    suspend fun addToFavs(pokemon: Pokemon){
//        _favPokes.add(pokemon)
//        Log.d("favs","$favsList")
        favPokemonRepository.addToFavs(pokemon)
    }
    suspend fun removeFromFavs(pokemon: FavoritePokemon){
        favPokemonRepository.deleteFromFavs(pokemon)
    }

    suspend fun getPokedexEntry(pokemon: FavoritePokemon):Pokemon{
        return favPokemonRepository.getDex(pokemon)
    }
}
