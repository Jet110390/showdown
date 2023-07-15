package com.example.showdown.data.repository

import com.example.showdown.data.local.daos.PokemonDao
import com.example.showdown.data.remote.models.IdOption
import javax.inject.Inject
import kotlin.random.Random

class WTPRepository@Inject constructor(
    private val pokemonDao: PokemonDao
) {
    val normalModeOptions: MutableList<IdOption> = mutableListOf()
    val easyModeOptions: MutableList<IdOption> = mutableListOf()
    var hardModeOption: IdOption? = null

    suspend fun getOptionsNormalMode():MutableList<IdOption>{
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
    suspend fun getOptionsEasyMode():MutableList<IdOption>{
        val optionIDs: MutableList<Int> = mutableListOf()
        while (easyModeOptions.size <= 1){
            val option = Random.nextInt(1, 1011)
            if(option !in optionIDs ){
                optionIDs.add(option)
                val gameOption = pokemonDao.getIdGameOption(option)
                easyModeOptions.add(gameOption)
            }
        }
        return easyModeOptions
    }

    suspend fun normalModeCorrectAnswer(): IdOption {
        val options = getOptionsNormalMode()
        return options.random()
    }
    suspend fun easyModeCorrectAnswer(): IdOption {
        val options = getOptionsEasyMode()
        return options.random()
    }

    suspend fun hardModeCorrectAnswer(): IdOption? {
        val option = Random.nextInt(1, 1011)
        hardModeOption = pokemonDao.getIdGameOption(option)
        return hardModeOption
    }

    fun clearEasyModeOptions(){
        easyModeOptions.clear()
    }
    fun clearNormalModeOptions(){
        normalModeOptions.clear()
    }
    fun clearHardModeOptions(){
        hardModeOption = null
    }
}