package com.example.claudiagalerapract2.data.remote.di.modelo

import com.example.claudiagalerapract2.domain.modelo.Comment

data class CommentRemote (
    val postId : Int,
    val id : Int,
    val name : String,
    val email: String,
    val body: String
)
fun CommentRemote.toComment() =
    Comment(
        postId = postId,
        id = id,
        name = name,
        email = email,
        body = body
    )
fun CommentRemote.toCommentDetail() =
    Comment(
        postId = postId,
        id = id,
        name = name,
        email = email,
        body = body
    )