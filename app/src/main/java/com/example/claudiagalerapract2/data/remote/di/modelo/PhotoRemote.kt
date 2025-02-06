package com.example.claudiagalerapract2.data.remote.di.modelo

import com.example.claudiagalerapract2.domain.modelo.Photo

data class PhotoRemote (
    val albumId : Int,
    val id : Int,
    val title : String,
    val url: String,
    val thumbnailUrl: String
)

fun PhotoRemote.toPhoto() =
    Photo(
        albumId = albumId,
        id = id,
        title = title,
        url = url,
        thumbnailUrl = thumbnailUrl
    )
fun PhotoRemote.toPhotoDetail() =
    Photo(
        albumId = albumId,
        id = id,
        title = title,
        url = url,
        thumbnailUrl = thumbnailUrl
    )