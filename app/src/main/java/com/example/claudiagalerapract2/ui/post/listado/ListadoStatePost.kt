package com.example.claudiagalerapract2.ui.post.listado

import com.example.claudiagalerapract2.domain.modelo.Post

data class ListadoStatePost (
    val posts : List<Post> = emptyList(),
    val error : String = "",
    val mensaje : String = ""
)