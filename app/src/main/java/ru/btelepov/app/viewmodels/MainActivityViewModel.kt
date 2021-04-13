package ru.btelepov.app.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.btelepov.app.models.JokeItem
import ru.btelepov.app.repository.MainRepository
import ru.btelepov.app.utilits.Resource
import java.io.IOException

class MainActivityViewModel(private val mainRepository: MainRepository, app: Application) :
    AndroidViewModel(app) {

    private var _responseGetRandomJoke: MutableLiveData<Resource<List<JokeItem>>> =
        MutableLiveData()

    val responseGetRandomJoke: LiveData<Resource<List<JokeItem>>> get() = _responseGetRandomJoke


    fun getRandomJoke() {
        viewModelScope.launch {
            safeCall()

        }
    }


    private fun handleResponse(response: Response<List<JokeItem>>): Resource<List<JokeItem>> {
        if (response.isSuccessful) {
            return Resource.Success(response.body()?.toList())
        }

        return Resource.Error(response.message())
    }

    private suspend fun safeCall() {
        _responseGetRandomJoke.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = mainRepository.getRandomJoke()
                _responseGetRandomJoke.postValue(handleResponse(response))
            } else {
                _responseGetRandomJoke.postValue(Resource.Error("No Internet Connection! :("))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _responseGetRandomJoke.postValue(Resource.Error("No Internet Connection! :("))
                else -> _responseGetRandomJoke.postValue(Resource.Error("Conversion Error"))
            }
        }
    }


    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }


}