package com.example.claudiagalerapract2.ui.pantallamain.state

import com.example.claudiagalerapract2.domain.modelo.Album


data class ListadoStateAlbum (
    val albums : List<Album> = emptyList()
)