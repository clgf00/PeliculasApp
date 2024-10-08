package com.example.claudiagalerapract2.ui.pantallaMain
import com.example.claudiagalerapract2.domain.modelo.Pelicula

data class FichaState (
    val pelicula: Pelicula = Pelicula(),
    val habilitarSiguiente: Boolean = false,
    val habilitaAnterior: Boolean = false,
    val seekBarValue: Int = 0,
    val error: String? = null
    //El "?" hace que funcione solo si no es null

)