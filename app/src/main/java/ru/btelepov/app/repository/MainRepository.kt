package ru.btelepov.app.repository

import retrofit2.Response
import ru.btelepov.app.models.JokeItem
import ru.btelepov.app.network.RetrofitInstance

class MainRepository {

    suspend fun getRandomJoke(): Response<List<JokeItem>> {
        return RetrofitInstance.api.getRandomJoke()
    }
}