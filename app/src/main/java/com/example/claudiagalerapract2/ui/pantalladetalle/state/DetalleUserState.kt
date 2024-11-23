package com.example.claudiagalerapract2.ui.pantalladetalle.state
import com.example.claudiagalerapract2.domain.modelo.User

//F
data class DetalleUserState (
    val user: User? = User(),
    val mensaje: String? = null,
)