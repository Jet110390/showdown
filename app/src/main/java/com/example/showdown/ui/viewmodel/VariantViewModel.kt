package com.example.showdown.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.showdown.data.local.entities.Pokemon
import com.example.showdown.data.local.entities.Variant
import com.example.showdown.data.remote.models.Urls
import com.example.showdown.data.repository.PokemonInfoRepository
import com.example.showdown.data.repository.VariantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VariantViewModel @Inject constructor(
    private val pokemonInfoRepository: PokemonInfoRepository,
    private val variantRepository: VariantRepository
//    @Inject val pokemon: Pokemon
): ViewModel() {
    private var _variantData: MutableLiveData<List<Variant>?> = MutableLiveData()
    val variantData: LiveData<List<Variant>?> get() = _variantData

//    init{
//        getVariantInfo(pokemon)
//    }

    fun getVariantInfo(pokemon: Pokemon){
        viewModelScope.launch(Dispatchers.Default) {
            val response = pokemonInfoRepository.getVariants(pokemon)
            _variantData.postValue(response)
        }
    }

    suspend fun addToFavs(pokemon: Variant){
//        _favPokes.add(pokemon)
//        Log.d("favs","$favsList")
        variantRepository.addToFavs(pokemon)
    }
}

