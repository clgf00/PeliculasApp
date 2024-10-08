package com.example.claudiagalerapract2.domain.modelo

class Pelicula (
    val titulo: String = "",
    val anyoEstreno: Int? = null,
    val director: String? = null,
    val genero: String? = null,
    val recomendado: Boolean = false.not(),
    val calificacion: Int = 0

)