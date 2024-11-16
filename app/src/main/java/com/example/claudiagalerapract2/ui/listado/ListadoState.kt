package com.example.claudiagalerapract2.ui.listado

import com.example.claudiagalerapract2.domain.modelo.Hero
import com.example.claudiagalerapract2.ui.common.UiEvent

data class ListadoState(
    val heroes: List<Hero> = emptyList(),
    val isLoading: Boolean = false,
    val event: UiEvent? = null,
)
