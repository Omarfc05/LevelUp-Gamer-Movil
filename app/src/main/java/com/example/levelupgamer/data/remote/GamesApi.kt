package com.example.levelupgamer.data.remote


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GamesApi {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://www.mmobomb.com/api1/") // 👈 base de la API gamer
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: GamesApiService = retrofit.create(GamesApiService::class.java)
}
