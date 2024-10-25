package com.example.claudiagalerapract2.ui.pantalladetalle
import com.example.claudiagalerapract2.domain.modelo.Pelicula

data class DetalleState (
    val id: Int = 0,
    val pelicula: Pelicula = Pelicula(),
    val habilitarSiguiente: Boolean = true,
    val habilitarAnterior: Boolean = false,
    val seekBarValue: Double = 0.0,
    val mensaje: String? = null,
    val peliculas: List<Pelicula> = emptyList()
)