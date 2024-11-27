package com.example.claudiagalerapract2.ui.pantalladetalle.state

import com.example.claudiagalerapract2.domain.modelo.Album
import com.example.claudiagalerapract2.domain.modelo.Photo

data class DetalleAlbumState (
    val album: Album? = Album(),
    val photos : List<Photo> = emptyList(),
    val mensaje: String? = null,
)