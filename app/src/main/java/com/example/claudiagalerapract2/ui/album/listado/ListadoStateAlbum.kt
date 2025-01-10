package com.example.claudiagalerapract2.ui.album.listado

import com.example.claudiagalerapract2.domain.modelo.Album


data class ListadoStateAlbum(
    val albums: List<Album> = emptyList(),
    val error: String = "",
    val mensaje: String = "",
    val isLoading: Boolean = false
)