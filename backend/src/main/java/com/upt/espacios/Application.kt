package com.upt.espacios

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {

        DatabaseFactory.init()

        install(ContentNegotiation) {
            json()
        }

        val userService = UserService()
        val spaceService = SpaceService()

        routing {
            get("/") {
                call.respondText("Servidor Ktor en funcionamiento")
            }

            // Rutas de Usuarios
            route("/api/usuarios") {
                get {
                    val users = userService.getAll()
                    call.respond(users)
                }

                post {
                    val user = call.receive<User>()
                    val newId = userService.create(user)
                    call.respond(mapOf("status" to "success", "id" to newId))
                }
            }

            // Rutas de Espacios
            route("/api/espacios") {
                get {
                    val spaces = spaceService.getAll()
                    call.respond(spaces)
                }

                post {
                    val space = call.receive<Space>()
                    val newId = spaceService.create(space)
                    call.respond(mapOf("status" to "success", "id" to newId))
                }
            }
        }
    }.start(wait = true)
}