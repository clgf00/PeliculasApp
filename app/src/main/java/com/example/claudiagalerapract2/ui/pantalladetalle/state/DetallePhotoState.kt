package com.example.claudiagalerapract2.ui.pantalladetalle.state

import com.example.claudiagalerapract2.domain.modelo.Photo

data class DetallePhotoState (
    val photo: Photo? = Photo(),
    val mensaje: String? = null,
)