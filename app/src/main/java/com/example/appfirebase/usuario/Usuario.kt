package com.example.appfirebase.usuario

data class Usuario(
    val id: String?=null,
    var nombre: String? = null,
    var telefono: String? = null,
    var correo: String? = null,
    var domicilio: String? = null
)