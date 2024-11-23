package com.example.claudiagalerapract2.ui.pantalladetalle.state

import com.example.claudiagalerapract2.domain.modelo.Album

data class DetalleAlbumState (
    val album: Album? = Album(),
    val mensaje: String? = null,
)