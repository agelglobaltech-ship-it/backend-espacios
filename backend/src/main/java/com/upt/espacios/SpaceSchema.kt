package com.upt.espacios

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import kotlinx.serialization.Serializable

@Serializable
data class Space(
    val id: Int? = null,
    val nombre: String,
    val idEdificio: Int,
    val tipo: String,
    val capacidad: Int
)

object Spaces : Table("espacios") {
    val id = integer("id_espacio").autoIncrement()
    val nombre = varchar("nombre", 100)
    val idEdificio = integer("id_edificio")
    val tipo = varchar("tipo", 50)
    val capacidad = integer("capacidad")

    override val primaryKey = PrimaryKey(id)
}

class SpaceService {
    suspend fun getAll(): List<Space> = transaction {
        Spaces.selectAll().map {
            Space(
                id = it[Spaces.id],
                nombre = it[Spaces.nombre],
                idEdificio = it[Spaces.idEdificio],
                tipo = it[Spaces.tipo],
                capacidad = it[Spaces.capacidad]
            )
        }
    }

    suspend fun create(space: Space): Int = transaction {
        Spaces.insert {
            it[nombre] = space.nombre
            it[idEdificio] = space.idEdificio
            it[tipo] = space.tipo
            it[capacidad] = space.capacidad
        } get Spaces.id
    }
}