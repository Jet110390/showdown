package com.example.showdown.ui.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.showdown.data.remote.models.IdOption
import com.example.showdown.data.repository.WTPRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class IDGameViewModel @Inject constructor(
    private val idGameRepository: WTPRepository
): ViewModel() {

    private val _normalModeOptions: MutableLiveData<MutableList<IdOption>?> = MutableLiveData()
    var normalModeOptions: LiveData<MutableList<IdOption>?> = _normalModeOptions
    private val _easyModeOptions: MutableLiveData<MutableList<IdOption>?> = MutableLiveData()
    var easyModeOptions: LiveData<MutableList<IdOption>?> = _easyModeOptions
    private val _answerName : MutableLiveData<String?> = MutableLiveData()
    var answerName : LiveData<String?> = _answerName
    private val _answerImage: MutableLiveData<String?> = MutableLiveData()
    var answerImage : LiveData<String?> = _answerImage
    private val _choice: MutableLiveData<String?> = MutableLiveData()
    var choice : LiveData<String?> =_choice
    private val _correctOrIncorrectAnswer: MutableLiveData<Boolean?> = MutableLiveData()
    var correctOrIncorrectAnswer : LiveData<Boolean?> =_correctOrIncorrectAnswer
    private val _mode: MutableLiveData<String?> = MutableLiveData()
    var mode : LiveData<String?> =_mode


    fun getGameData(){
        viewModelScope.async(Default) {
            var answer: IdOption? = null
            when(mode.value){
                "Easy" -> {
                    answer = idGameRepository.easyModeCorrectAnswer()
                    _easyModeOptions.postValue(idGameRepository.easyModeOptions)}
                "Normal" ->{
                    answer = idGameRepository.normalModeCorrectAnswer()
                    _normalModeOptions.postValue(idGameRepository.normalModeOptions)}
                "Hard" -> answer = idGameRepository.hardModeCorrectAnswer()
            }
            _answerName.postValue(answer?.name)
            _answerImage.postValue(answer?.image)
        }
    }

    fun selectGameMode(gameMode: String){
        Log.d(TAG, "gameViewModel select game mode parameter is $gameMode")
        _mode.postValue(gameMode)
    }

    fun chooseAnswer(option: String){
        _choice.postValue(option)
    }

    fun clearGameData(){
        when(mode.value) {
            "Easy" -> idGameRepository.clearEasyModeOptions()
            "Normal" -> idGameRepository.clearNormalModeOptions()
            "Hard" -> idGameRepository.clearHardModeOptions()
        }
    }

    fun checkAnswer(choice:String, answer: String){
        _correctOrIncorrectAnswer.postValue(answer.equals(choice,true))
    }

    fun increaseDifficulty(){
        when(mode.value) {
            "Easy"-> _mode.postValue("Normal")
            "Normal"-> _mode.postValue("Hard")
        }
    }
}