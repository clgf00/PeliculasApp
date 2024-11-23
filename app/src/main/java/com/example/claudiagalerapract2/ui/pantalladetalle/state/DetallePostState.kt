package com.example.claudiagalerapract2.ui.pantalladetalle.state

import com.example.claudiagalerapract2.domain.modelo.Post

data class DetallePostState(
    val post: Post? = Post(),
    val mensaje: String? = null,
)