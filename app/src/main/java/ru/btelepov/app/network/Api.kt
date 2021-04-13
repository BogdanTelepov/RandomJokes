package ru.btelepov.app.network

import retrofit2.Response
import retrofit2.http.GET
import ru.btelepov.app.models.JokeItem
import ru.btelepov.app.utilits.GET_RANDOM_JOKE

interface Api {


    @GET(GET_RANDOM_JOKE)
    suspend fun getRandomJoke(): Response<List<JokeItem>>
}