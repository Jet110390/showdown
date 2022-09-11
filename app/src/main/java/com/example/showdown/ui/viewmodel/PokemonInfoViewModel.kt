package com.example.showdown.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.showdown.data.local.entities.FavoritePokemon
import com.example.showdown.data.local.entities.Pokemon
import com.example.showdown.data.local.entities.Variant
import com.example.showdown.data.remote.models.DexEntry
import com.example.showdown.data.remote.models.SpeciesEntry
import com.example.showdown.data.remote.models.Urls
import com.example.showdown.data.remote.models.stats.PokeInfoDto
import com.example.showdown.data.remote.models.urls.ResponseDto
import com.example.showdown.data.repository.PokemonInfoRepository
import com.example.showdown.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import retrofit2.http.Url
import javax.inject.Inject

@HiltViewModel
class PokemonInfoViewModel @Inject constructor(
    private val pokemonInfoRepository: PokemonInfoRepository
): ViewModel() {

    private var _pokemonUrls: MutableLiveData<List<Urls>?> = MutableLiveData()
    val pokemonUrls: LiveData<List<Urls>?> get() = _pokemonUrls

    private var pokeInfo: MutableList<PokeInfoDto> = mutableListOf()

    private var speciesInfo: MutableList<SpeciesEntry> = mutableListOf()
//    private var speciesInfo: MutableList<SpeciesEntry> = mutableListOf()

    val favsList:MutableList<Pokemon> = mutableListOf()
    private var _fullPokedex: MutableLiveData<DataState<List<Pokemon>>> = MutableLiveData()
    val fullPokedex: LiveData<DataState<List<Pokemon>>> get() = _fullPokedex

    init {
        getPokeInfo()
    }

//    private fun getPokemonUrls() {
//        viewModelScope.launch(IO) {
//
//            val response = pokemonInfoRepository.getPokeUrls()
//            _pokemonUrls.postValue(response)
//
//        }
//    }

    private fun getPokeInfo() {

        viewModelScope.launch(Default) {
            val response = pokemonInfoRepository.getPokeUrls()
            _pokemonUrls.postValue(response)
             response?.let { it ->

                it.map {
                    pokemonInfoRepository.getPokemonInfo(it)?.let { it1 -> pokeInfo.add(it1) }
//                    pokemonInfoRepository.getSpecies(it)?.let { it1 -> speciesInfo.add(it1) }
//                        Log.d("innerRes","${pokeInfo[0].name} ${speciesInfo[0]}")
                    }
            }
            speciesInfo=pokemonInfoRepository.getSpecies(pokeInfo)


            val pokedex=pokemonInfoRepository.getPokedex(pokeInfo,speciesInfo)
            _fullPokedex.postValue(pokedex)
        }
    }
//    val fullPokedex = pokemonInfoRepository.getPokedex( pokeInfo,speciesInfo).asLiveData(viewModelScope.coroutineContext)

    suspend fun addToFavs(pokemon: Pokemon){
//        favsList.add(pokemon)
        Log.d("favs","$favsList")
        pokemonInfoRepository.addToFavs(pokemon)
    }

//

}