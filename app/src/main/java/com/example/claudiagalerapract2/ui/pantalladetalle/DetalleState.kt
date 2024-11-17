package com.example.claudiagalerapract2.ui.pantalladetalle
import com.example.claudiagalerapract2.domain.modelo.Hero

data class DetalleState (
    val hero: Hero? = Hero(),
    val mensaje: String? = null,
)