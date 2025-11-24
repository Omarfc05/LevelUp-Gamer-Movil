package com.example.levelupgamer.data.remote




import com.example.levelupgamer.model.ApiProduct
import retrofit2.http.GET

interface ProductApi {
    @GET("products")
    suspend fun getProducts(): List<ApiProduct>
}
