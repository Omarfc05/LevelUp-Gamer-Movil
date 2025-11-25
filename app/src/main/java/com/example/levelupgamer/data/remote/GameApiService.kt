package com.example.levelupgamer.data.remote


import com.example.levelupgamer.model.ApiProduct
import retrofit2.http.GET

interface GamesApiService {

    @GET("games")
    suspend fun getGames(): List<ApiProduct>
}
