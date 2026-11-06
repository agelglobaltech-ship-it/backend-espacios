package com.upt.espacios.network

import com.upt.espacios.model.Espacio
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("api/espacios")
    suspend fun getEspacios(): Response<List<Espacio>>

    @POST("api/espacios")
    suspend fun crearEspacio(@Body espacio: Espacio): Response<Map<String, Any>>
}
