package com.example.claudiagalerapract2.ui.pantallamain

import com.example.claudiagalerapract2.domain.modelo.Hero


data class MainState(
    val heroes: List<Hero> = emptyList(),
)