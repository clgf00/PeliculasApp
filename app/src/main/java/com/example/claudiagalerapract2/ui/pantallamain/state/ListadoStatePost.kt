package com.example.claudiagalerapract2.ui.pantallamain.state

import com.example.claudiagalerapract2.domain.modelo.Post

data class ListadoStatePost (
    val posts : List<Post> = emptyList()
)