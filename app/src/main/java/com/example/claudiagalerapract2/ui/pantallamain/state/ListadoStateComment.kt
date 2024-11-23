package com.example.claudiagalerapract2.ui.pantallamain.state

import com.example.claudiagalerapract2.domain.modelo.Comment


data class ListadoStateComment (
    val comments : List<Comment> = emptyList()
)