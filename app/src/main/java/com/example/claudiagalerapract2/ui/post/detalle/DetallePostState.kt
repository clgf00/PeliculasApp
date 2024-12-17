package com.example.claudiagalerapract2.ui.post.detalle

import com.example.claudiagalerapract2.domain.modelo.Comment
import com.example.claudiagalerapract2.domain.modelo.Post

data class DetallePostState(
    val error : String = "",
    val post: Post? = Post(),
    val posts: List<Post> = emptyList(),
    val comments: List<Comment> = emptyList(),
    val mensaje: String? = null,
)