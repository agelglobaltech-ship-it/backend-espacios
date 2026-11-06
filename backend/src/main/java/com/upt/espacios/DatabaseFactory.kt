package com.upt.espacios

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val driverClassName = "com.mysql.cj.jdbc.Driver"
        val jdbcUrl = "jdbc:mysql://localhost:3306/encuentra_tu_espacio?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
        val user = "root"
        val password = "root" // Cambia esto si tu MySQL local tiene otra contraseña

        try {
            println("Intentando conectar a MySQL Local (${jdbcUrl})...")
            Database.connect(
                url = jdbcUrl,
                driver = driverClassName,
                user = user,
                password = password
            )
            println("✅ Conexión exitosa a MySQL Local (encuentra_tu_espacio)")

            transaction {
                // Si la tabla ya existe con tus datos, no necesitamos recrearla
                // SchemaUtils.create(Users)
                println("✅ Base de datos local lista.")
            }
        } catch (e: Exception) {
            println("❌ ERROR CRÍTICO al conectar o inicializar la base de datos MySQL:")
            e.printStackTrace()
            throw e
        }
    }
}
