package com.upt.espacios.model

import com.google.gson.annotations.SerializedName

data class Espacio(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("idEdificio")
    val idEdificio: Int = 1,

    @SerializedName("tipo")
    val tipo: String,

    @SerializedName("capacidad")
    val capacidad: Int
)
