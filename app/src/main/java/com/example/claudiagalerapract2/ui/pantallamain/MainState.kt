package com.example.claudiagalerapract2.ui.pantallamain

import com.example.claudiagalerapract2.domain.modelo.Pelicula


data class MainState(
    val peliculas: List<Pelicula> = emptyList(),
    val isIrDetalle: Boolean = false,
)