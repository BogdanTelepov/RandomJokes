package ru.btelepov.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.btelepov.app.models.JokeItem
import ru.btelepov.app.repository.MainRepository

class MainActivityViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private var _responseGetRandomJoke: MutableLiveData<Response<List<JokeItem>>> =
        MutableLiveData()

    val responseGetRandomJoke: LiveData<Response<List<JokeItem>>> get() = _responseGetRandomJoke


    fun getRandomJoke() {
        viewModelScope.launch {
            val response = mainRepository.getRandomJoke()
            _responseGetRandomJoke.value = response
        }
    }


}