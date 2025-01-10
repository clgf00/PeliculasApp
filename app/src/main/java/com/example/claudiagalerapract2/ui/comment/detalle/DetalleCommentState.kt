package com.example.claudiagalerapract2.ui.comment.detalle

import com.example.claudiagalerapract2.domain.modelo.Comment

data class DetalleCommentState (
    val comment: Comment? = Comment(),
    val comments: List<Comment> = emptyList(),
    val error : String = "",
    val mensaje: String? = null,
    val isLoading : Boolean = false,
)