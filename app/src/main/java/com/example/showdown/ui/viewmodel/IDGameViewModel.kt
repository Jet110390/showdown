package com.example.showdown.ui.viewmodel

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

    private val _options: MutableLiveData<MutableList<IdOption>?> = MutableLiveData()
    var options: LiveData<MutableList<IdOption>?> = _options
    private val _answerName : MutableLiveData<String?> = MutableLiveData()
    var answerName : LiveData<String?> = _answerName
    private val _answerImage: MutableLiveData<String?> = MutableLiveData()
    var answerImage : LiveData<String?> = _answerImage
    private val _choice: MutableLiveData<String?> = MutableLiveData()
    var choice : LiveData<String?> =_choice
    private val _correctOrIncorrectAnswer: MutableLiveData<Boolean?> = MutableLiveData()
    var correctOrIncorrectAnswer : LiveData<Boolean?> =_correctOrIncorrectAnswer


    fun getGameData(){
        viewModelScope.async(Default) {
            val answer = idGameRepository.correctAnswer()
            _options.postValue(idGameRepository.normalModeOptions)
            _answerName.postValue(answer.name)
            _answerImage.postValue(answer.image)
        }
    }

    fun chooseAnswer(option: String){
        _choice.postValue(option)
    }

    fun clearGameData(){
        idGameRepository.clearOptions()
    }

    fun checkAnswer(choice:String, answer: String){
        _correctOrIncorrectAnswer.postValue(choice == answer)
    }
}