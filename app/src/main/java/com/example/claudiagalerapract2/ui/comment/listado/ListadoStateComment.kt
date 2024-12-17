package com.example.claudiagalerapract2.ui.comment.listado

import com.example.claudiagalerapract2.domain.modelo.Comment


data class ListadoStateComment (
    val comments : List<Comment> = emptyList()
)