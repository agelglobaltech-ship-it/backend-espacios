package com.upt.espacios

import org.jetbrains.exposed.sql.Database

object DatabaseFactory {
    fun init() {
        val driverClassName = "com.mysql.cj.jdbc.Driver"
        val jdbcUrl = "jdbc:mysql://mysql-1ccc7e92-agelglobal-0dac.k.aivencloud.com:18787/defaultdb?sslMode=REQUIRED"
        val user = "avnadmin"
        val password = "AVNS_NKungoS8xQI3E1HGobT"

        Database.connect(
            url = jdbcUrl,
            driver = driverClassName,
            user = user,
            password = password
        )
        println("✅ Conexión exitosa a MySQL Aiven (defaultdb)")
    }
}