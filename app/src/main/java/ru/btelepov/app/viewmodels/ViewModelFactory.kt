package ru.btelepov.app.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.btelepov.app.repository.MainRepository
import java.lang.IllegalArgumentException

class ViewModelFactory(private val mainRepository: MainRepository, val app: Application) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainActivityViewModel::class.java) -> MainActivityViewModel(
                mainRepository, app
            ) as T

            else -> throw IllegalArgumentException("ViewModel not found :(")
        }
    }
}