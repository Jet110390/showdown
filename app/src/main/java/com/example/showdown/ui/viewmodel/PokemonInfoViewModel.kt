package com.example.showdown.ui.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import com.example.showdown.data.local.entities.Pokemon
import com.example.showdown.data.remote.models.SpeciesEntry
import com.example.showdown.data.remote.models.Urls
import com.example.showdown.data.remote.models.stats.PokeInfoDto
import com.example.showdown.data.repository.PokemonInfoRepository
import com.example.showdown.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureTimeMillis

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
    private var _gen1: MutableLiveData<List<Pokemon>> = MutableLiveData()
    val gen1: LiveData<List<Pokemon>> get() = _gen1
    private var _gen2: MutableLiveData<List<Pokemon>> = MutableLiveData()
    val gen2: LiveData<List<Pokemon>> get() = _gen2
    private var _gen3: MutableLiveData<List<Pokemon>> = MutableLiveData()
    val gen3: LiveData<List<Pokemon>> get() = _gen3
    private var _gen4: MutableLiveData<List<Pokemon>> = MutableLiveData()
    val gen4: LiveData<List<Pokemon>> get() = _gen4
    private var _gen5: MutableLiveData<List<Pokemon>> = MutableLiveData()
    val gen5: LiveData<List<Pokemon>> get() = _gen5
    private var _gen6: MutableLiveData<List<Pokemon>> = MutableLiveData()
    val gen6: LiveData<List<Pokemon>> get() = _gen6
    private var _gen7: MutableLiveData<List<Pokemon>> = MutableLiveData()
    val gen7: LiveData<List<Pokemon>> get() = _gen7

    private var _gen8: MutableLiveData<List<Pokemon>> = MutableLiveData()
    val gen8: LiveData<List<Pokemon>> get() = _gen8

    private var _gen9: MutableLiveData<List<Pokemon>> = MutableLiveData()
    val gen9: LiveData<List<Pokemon>> get() = _gen9

    private var _allPokes: MutableLiveData<List<Pokemon>> = MutableLiveData()
    val allPokes: LiveData<List<Pokemon>> get() = _allPokes
    private var _officialImg: MutableLiveData<String> = MutableLiveData()
    val officialImg: LiveData<String> get() = _officialImg

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
//        first way
//        viewModelScope.launch(Default) {
//            val response = pokemonInfoRepository.getPokeUrls()
//            _pokemonUrls.postValue(response)
//             response?.let { it ->
//
//                it.map {
//                    pokemonInfoRepository.getPokemonInfo(it)?.let { it1 -> pokeInfo.add(it1) }
////                    pokemonInfoRepository.getSpecies(it)?.let { it1 -> speciesInfo.add(it1) }
////                        Log.d("innerRes","${pokeInfo[0].name} ${speciesInfo[0]}")
//                    }
//            }
//            speciesInfo=pokemonInfoRepository.getSpeciesList(pokeInfo)
//
//
//            val pokedex=pokemonInfoRepository.getPokedexList(pokeInfo,speciesInfo)
//            _fullPokedex.postValue(pokedex)
//        }

//        second way
//        for (i in 1..1010) {
//            viewModelScope.async(IO) {
//                val response = pokemonInfoRepository.getPokeInfoViaDexNumber(i.toString())
//                response?.let { poke ->
//                    pokeInfo.add(poke)
//                }
//            }
//        }
//        viewModelScope.async(IO) {
//            speciesInfo = pokemonInfoRepository.getSpeciesList(pokeInfo)
//        }
//        viewModelScope.async(IO) {
//            val pokedex = pokemonInfoRepository.getPokedexList(pokeInfo,speciesInfo)
//            _fullPokedex.postValue(pokedex)
//        }

//        third way
        val time = measureTimeMillis{
            viewModelScope.async(IO) {
                val response = pokemonInfoRepository.getPokedexList2()
                response.let {
                    _fullPokedex.postValue(it)
                }
            }
        }
        Log.d(TAG, "Request took $time")

    }


//    val fullPokedex = pokemonInfoRepository.getPokedex( pokeInfo,speciesInfo).asLiveData(viewModelScope.coroutineContext)

    suspend fun addToFavs(pokemon: Pokemon){
//        favsList.add(pokemon)
        Log.d("favs","$favsList")
        pokemonInfoRepository.addToFavs(pokemon)
    }
    fun getGenOne(): LiveData<List<Pokemon>> {
        viewModelScope.launch(Default){
            val response = pokemonInfoRepository.getGenerationOne()
            _gen1.postValue(response)
        }
        return gen1
    }
    fun getGenTwo(): LiveData<List<Pokemon>> {viewModelScope.launch(Default){
        val response = pokemonInfoRepository.getGenerationTwo()
        _gen2.postValue(response)
    }
        return gen2}
    fun getGenThree(): LiveData<List<Pokemon>> {viewModelScope.launch(Default){
        val response = pokemonInfoRepository.getGenerationThree()
        _gen3.postValue(response)
        }
        return gen3}
    fun getGenFour(): LiveData<List<Pokemon>> {viewModelScope.launch(Default){
        val response = pokemonInfoRepository.getGenerationFour()
        _gen4.postValue(response)
        }
        return gen4}
    fun getGenFive(): LiveData<List<Pokemon>> {viewModelScope.launch(Default){
        val response = pokemonInfoRepository.getGenerationFive()
        _gen5.postValue(response)
        }
        return gen5}
    fun getGenSix(): LiveData<List<Pokemon>> {viewModelScope.launch(Default){
        val response = pokemonInfoRepository.getGenerationSix()
        _gen6.postValue(response)
        }
        return gen6}
    fun getGenSeven(): LiveData<List<Pokemon>> {viewModelScope.launch(Default){
        val response = pokemonInfoRepository.getGenerationSeven()
        _gen7.postValue(response)
        }
        return gen7}
    fun getGenEight(): LiveData<List<Pokemon>> {viewModelScope.launch(Default){
        val response = pokemonInfoRepository.getGenerationEight()
        _gen8.postValue(response)
        }
        return gen8}

    fun getGenNine(): LiveData<List<Pokemon>> {viewModelScope.launch(Default){
        val response = pokemonInfoRepository.getGenerationNine()
        _gen9.postValue(response)
        }
        return gen9}
    fun allPokes(): LiveData<List<Pokemon>> {viewModelScope.launch(Default){
        val response = pokemonInfoRepository.fullDexList()
        _allPokes.postValue(response)
    }
        return allPokes}
    fun getOfficialImage(id: Int): LiveData<String>{
        viewModelScope.launch(Default){
            val response = pokemonInfoRepository.getOfficialImg(id)
            _officialImg.postValue(response)
    }
        return officialImg
    }
}