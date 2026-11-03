package com.upt.espacios

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int? = null,
    val nombre: String,
    val correo: String,
    val password: String,
    val rol: String
)

object Users : Table("usuarios") {
    val id = integer("id_usuario").autoIncrement()
    val nombre = varchar("nombre", 100)
    val correo = varchar("correo", 100).uniqueIndex()
    val password = varchar("password", 255)
    val rol = varchar("rol", 50)

    override val primaryKey = PrimaryKey(id)
}

class UserService {
    suspend fun getAll(): List<User> = transaction {
        Users.selectAll().map {
            User(
                id = it[Users.id],
                nombre = it[Users.nombre],
                correo = it[Users.correo],
                password = it[Users.password],
                rol = it[Users.rol]
            )
        }
    }

    suspend fun create(user: User): Int = transaction {
        Users.insert {
            it[nombre] = user.nombre
            it[correo] = user.correo
            it[password] = user.password
            it[rol] = user.rol
        } get Users.id
    }
}