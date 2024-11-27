package com.example.claudiagalerapract2.ui.pantalladetalle.state

import com.example.claudiagalerapract2.domain.modelo.Comment
import com.example.claudiagalerapract2.domain.modelo.Post

data class DetallePostState(
    val post: Post? = Post(),
    val comments: List<Comment> = emptyList(),
    val mensaje: String? = null,
)