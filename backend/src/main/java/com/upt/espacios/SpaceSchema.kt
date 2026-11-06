package com.upt.espacios

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import kotlinx.serialization.Serializable

@Serializable
data class Space(
    val id: Int? = null,
    val idEdificio: Int,
    val codigo: String,
    val nombre: String,
    val planta: Int,
    val capacidad: Int,
    val tipo: String,
    val estado: String
)

object Spaces : Table("espacios") {
    val id = integer("id_espacio").autoIncrement()
    val idEdificio = integer("id_edificio")
    val codigo = varchar("codigo", 50)
    val nombre = varchar("nombre", 100)
    val planta = integer("planta")
    val capacidad = integer("capacidad")
    val tipo = varchar("tipo", 50)
    val estado = varchar("estado", 50)

    override val primaryKey = PrimaryKey(id)
}

class SpaceService {
    suspend fun getAll(): List<Space> = transaction {
        Spaces.selectAll().map {
            Space(
                id = it[Spaces.id],
                idEdificio = it[Spaces.idEdificio],
                codigo = it[Spaces.codigo],
                nombre = it[Spaces.nombre],
                planta = it[Spaces.planta],
                capacidad = it[Spaces.capacidad],
                tipo = it[Spaces.tipo],
                estado = it[Spaces.estado]
            )
        }
    }

    suspend fun create(space: Space): Int = transaction {
        Spaces.insert {
            it[idEdificio] = space.idEdificio
            it[codigo] = space.codigo
            it[nombre] = space.nombre
            it[planta] = space.planta
            it[capacidad] = space.capacidad
            it[tipo] = space.tipo
            it[estado] = space.estado
        } get Spaces.id
    }
}