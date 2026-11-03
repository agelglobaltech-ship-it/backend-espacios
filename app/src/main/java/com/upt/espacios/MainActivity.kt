package com.upt.espacios

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.upt.espacios.network.RetrofitClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ejecuta la prueba de API
        obtenerEspaciosDesdeApi()
    }

    private fun obtenerEspaciosDesdeApi() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getEspacios()
                if (response.isSuccessful) {
                    val listaEspacios = response.body()
                    Log.d("API_TOR", "✅ Conexión exitosa. Espacios obtenidos: ${listaEspacios?.size}")
                    listaEspacios?.forEach { espacio ->
                        Log.d("API_TOR", "Espacio: ${espacio.nombre} | Capacidad: ${espacio.capacidad}")
                    }
                } else {
                    Log.e("API_TOR", "❌ Error de servidor HTTP: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("API_TOR", "❌ Error de conexión: ${e.message}")
            }
        }
    }
}