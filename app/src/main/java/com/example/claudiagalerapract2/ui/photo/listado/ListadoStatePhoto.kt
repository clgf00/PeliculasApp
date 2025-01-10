package com.example.claudiagalerapract2.ui.photo.listado

import com.example.claudiagalerapract2.domain.modelo.Photo

data class ListadoStatePhoto(
    val photos : List<Photo> = emptyList(),
    val error : String ="",
    val mensaje : String="",
    val isLoading : Boolean= false
)