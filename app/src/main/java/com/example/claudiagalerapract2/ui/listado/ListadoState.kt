package com.example.claudiagalerapract2.ui.listado

import com.example.claudiagalerapract2.domain.modelo.Pelicula
import com.example.claudiagalerapract2.ui.common.UiEvent

data class ListadoState(
    val peliculas: List<Pelicula> = emptyList(),
    val isLoading: Boolean = false,
    val peliculasSeleccionadas: List<Pelicula> = emptyList(),
    val SelectMode: Boolean = false,
    val event: UiEvent? = null,
)
