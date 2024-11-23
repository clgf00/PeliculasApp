package com.example.claudiagalerapract2.ui.pantalladetalle.state

import com.example.claudiagalerapract2.domain.modelo.Comment

data class DetalleCommentState (
    val comment: Comment? = Comment(),
    val mensaje: String? = null,
)