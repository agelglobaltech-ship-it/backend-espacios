package com.upt.espacios

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.upt.espacios.ui.components.AdaptiveScaffold
import com.upt.espacios.ui.components.Screen
import com.upt.espacios.ui.screens.*
import com.upt.espacios.ui.theme.EncuentraTuEspacioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EncuentraTuEspacioTheme {
                var isLoggedIn by remember { mutableStateOf(false) }
                var currentRoute by remember { mutableStateOf(Screen.Inicio.route) }
                var selectedRoomForReservation by remember { mutableStateOf("Laboratorio de Cómputo 2") }

                Surface(modifier = Modifier.fillMaxSize()) {
                    if (!isLoggedIn) {
                        LoginScreen(onLoginSuccess = { isLoggedIn = true })
                    } else {
                        AdaptiveScaffold(
                            currentRoute = currentRoute,
                            onNavigate = { currentRoute = it }
                        ) { paddingValues ->
                            Box(modifier = Modifier.padding(paddingValues)) {
                                when (currentRoute) {
                                    Screen.Inicio.route -> DashboardScreen(
                                        onReserveClick = { espacio ->
                                            selectedRoomForReservation = espacio.title
                                            currentRoute = "reservar"
                                        }
                                    )
                                    Screen.Salones.route -> SalonesScreen(
                                        onReservarClick = { salon ->
                                            selectedRoomForReservation = salon.nombre
                                            currentRoute = "reservar"
                                        }
                                    )
                                    Screen.Mapa.route -> MapaScreen(
                                        onRoomSelected = { roomName ->
                                            selectedRoomForReservation = roomName
                                            currentRoute = "reservar"
                                        }
                                    )
                                    Screen.MisReservas.route -> MisReservasScreen(
                                        onNewReservationClick = {
                                            currentRoute = Screen.Salones.route
                                        }
                                    )
                                    Screen.Perfil.route -> {
                                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                            Text("Perfil de Usuario (Yareli)")
                                        }
                                    }
                                    "reservar" -> ReservarScreen(
                                        roomName = selectedRoomForReservation,
                                        onConfirmReservation = {
                                            currentRoute = Screen.MisReservas.route
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
