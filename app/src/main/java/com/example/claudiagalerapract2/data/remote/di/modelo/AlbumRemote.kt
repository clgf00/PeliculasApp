package com.example.claudiagalerapract2.data.remote.di.modelo

import com.example.claudiagalerapract2.domain.modelo.Album

data class AlbumRemote (
    val userId : Int,
    val id : Int,
    val title : String,
)

fun AlbumRemote.toAlbum() =
    Album(
        id = id,
        title = title,

    )
fun AlbumRemote.toAlbumDetail() =
    Album(
        id = id,
        title = title,

    )