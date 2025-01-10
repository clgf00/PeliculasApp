package com.example.claudiagalerapract2.ui.album.detalle

import com.example.claudiagalerapract2.domain.modelo.Album
import com.example.claudiagalerapract2.domain.modelo.Photo

data class DetalleAlbumState (
    val album: Album? = Album(),
    val photos : List<Photo> = emptyList(),
    val mensaje: String? = null,
    val error : String = "",
    val isLoading : Boolean = false,
)