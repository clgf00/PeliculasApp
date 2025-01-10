package com.example.claudiagalerapract2.ui.photo.detalle

import com.example.claudiagalerapract2.domain.modelo.Photo

data class DetallePhotoState (
    val photo: Photo? = Photo(),
    val mensaje: String? = null,
    val error : String = "",
    val isLoading : Boolean = false
)