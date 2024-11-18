package com.example.claudiagalerapract2.ui.pantalladetalle
import com.example.claudiagalerapract2.domain.modelo.Hero

//F
data class DetalleState (
    val hero: Hero? = Hero(),
    val mensaje: String? = null,
)