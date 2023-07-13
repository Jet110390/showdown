package com.example.showdown.data.repository

import com.example.showdown.data.local.daos.PokemonDao
import com.example.showdown.data.remote.models.IdOption
import javax.inject.Inject
import kotlin.random.Random

class WTPRepository@Inject constructor(
    private val pokemonDao: PokemonDao
) {
    val normalModeOptions: MutableList<IdOption> = mutableListOf()

    private suspend fun getOptionsNormalMode():MutableList<IdOption>{
        val optionIDs: MutableList<Int> = mutableListOf()
        while (normalModeOptions.size <= 3){
            val option = Random.nextInt(1, 1011)
            if(option !in optionIDs ){
                optionIDs.add(option)
                val gameOption = pokemonDao.getIdGameOption(option)
                normalModeOptions.add(gameOption)
            }
        }
        return normalModeOptions
    }

    suspend fun correctAnswer(): IdOption {
        val options = getOptionsNormalMode()
        return options.random()
    }

    fun clearOptions(){
        normalModeOptions.clear()
    }
}