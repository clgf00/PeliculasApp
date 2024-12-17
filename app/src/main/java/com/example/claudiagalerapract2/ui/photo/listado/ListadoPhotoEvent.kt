package com.example.claudiagalerapract2.ui.photo.listado

sealed class ListadoPhotoEvent {
    data object GetPhotos : ListadoPhotoEvent()
    data class DeletePhoto(val photoId: Int) : ListadoPhotoEvent()
    data class FilterPhotos(val query: String) : ListadoPhotoEvent()

}