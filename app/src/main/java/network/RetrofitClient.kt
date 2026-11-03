package com.upt.espacios.network

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Modelo Space dentro del paquete de red
data class Space(
    val id: Int? = null,
    val nombre: String? = null,
    val capacidad: Int? = null,
    val descripcion: String? = null
)

interface ApiService {
    @GET("api/espacios")
    suspend fun getEspacios(): Response<List<Space>>
}

object RetrofitClient {
    private const val BASE_URL = "http://192.168.0.195:8080/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}