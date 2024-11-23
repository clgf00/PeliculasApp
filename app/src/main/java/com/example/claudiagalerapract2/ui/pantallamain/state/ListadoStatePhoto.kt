package com.example.claudiagalerapract2.ui.pantallamain.state

import com.example.claudiagalerapract2.domain.modelo.Photo

data class ListadoStatePhoto(
    val photos : List<Photo> = emptyList()
)