package com.upt.espacios

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*

fun main() {
    try {
        println("🚀 Iniciando servidor Ktor backend...")
        
        try {
            DatabaseFactory.init()
        } catch (e: Exception) {
            println("❌ Error crítico conectando a BD al iniciar: ${e.message}")
            e.printStackTrace()
            throw e
        }

        embeddedServer(Netty, port = 8080, host = "0.0.0.0") {

            install(ContentNegotiation) {
                json()
            }

            val userService = UserService()
            val spaceService = SpaceService()

            routing {
                get("/") {
                    call.respondText("Servidor Ktor en funcionamiento 🚀")
                }

                // Rutas de Usuarios
                route("/api/usuarios") {
                    get {
                        try {
                            val users = userService.getAll()
                            call.respond(users)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            val errorMsg = "${e.javaClass.simpleName}: ${e.message ?: "Error desconocido"}"
                            call.respond(HttpStatusCode.InternalServerError, mapOf("error" to errorMsg))
                        }
                    }

                    post {
                        try {
                            val user = call.receive<User>()
                            val newId = userService.create(user)
                            call.respond(mapOf("status" to "success", "id" to newId))
                        } catch (e: Exception) {
                            e.printStackTrace()
                            val errorMsg = "${e.javaClass.simpleName}: ${e.message ?: "Error desconocido"}"
                            call.respond(HttpStatusCode.InternalServerError, mapOf("error" to errorMsg))
                        }
                    }
                }

                // Rutas de Espacios
                route("/api/espacios") {
                    get {
                        try {
                            val spaces = spaceService.getAll()
                            call.respond(spaces)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            val errorMsg = "${e.javaClass.simpleName}: ${e.message ?: "Error desconocido"}"
                            call.respond(HttpStatusCode.InternalServerError, mapOf("error" to errorMsg))
                        }
                    }

                    post {
                        try {
                            val space = call.receive<Space>()
                            val newId = spaceService.create(space)
                            call.respond(mapOf("status" to "success", "id" to newId))
                        } catch (e: Exception) {
                            e.printStackTrace()
                            val errorMsg = "${e.javaClass.simpleName}: ${e.message ?: "Error desconocido"}"
                            call.respond(HttpStatusCode.InternalServerError, mapOf("error" to errorMsg))
                        }
                    }
                }
            }
        }.start(wait = true)
    } catch (e: Throwable) {
        System.err.println("❌ ERROR FATAL EN MAIN:")
        e.printStackTrace()
    }
}
