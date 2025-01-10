package com.example.claudiagalerapract2.data.remote.di.modelo

import com.example.claudiagalerapract2.domain.modelo.Post

data class PostRemote(
    val userId : Int,
    val id : Int,
    val title : String,
    val body: String,
)

fun PostRemote.toPost() =
    Post(
        id = id,
        title = title,
        body = body
    )
fun PostRemote.toPostDetail() =
    Post(
        id = id,
        title = title,
        body = body

    )