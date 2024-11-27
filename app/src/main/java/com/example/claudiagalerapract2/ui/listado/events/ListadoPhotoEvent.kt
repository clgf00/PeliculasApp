package com.example.claudiagalerapract2.ui.listado.events

sealed class ListadoPhotoEvent {
    data object GetPhotos : ListadoPhotoEvent()
    data class DeletePhotos(val photoId: Int) : ListadoPhotoEvent()
}