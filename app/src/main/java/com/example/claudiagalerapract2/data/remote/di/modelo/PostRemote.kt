package com.example.claudiagalerapract2.data.remote.di.modelo

import com.example.claudiagalerapract2.domain.modelo.Post
import com.example.claudiagalerapract2.domain.modelo.Todo

data class PostRemote(
    val userId : Int,
    val id : Int,
    val title : String,
    val body: String,
)

fun PostRemote.toPost() =
    Post(
        userId = userId,
        id = id,
        title = title,
        body = body
    )
fun PostRemote.toPostDetail() =
    Post(
        userId = userId,
        id = id,
        title = title,
        body = body

)