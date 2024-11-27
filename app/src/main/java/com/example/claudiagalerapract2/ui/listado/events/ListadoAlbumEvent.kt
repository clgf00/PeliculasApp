package com.example.claudiagalerapract2.ui.listado.events

sealed class ListadoAlbumEvent {
    data object GetAlbums : ListadoAlbumEvent()
    data class DeleteAlbum(val albumId: Int) : ListadoAlbumEvent()
    data class UpdateAlbum(val albumId: Int, val updatedContent: String) : ListadoAlbumEvent()
    data class AddAlbum(val newAlbumContent: String) : ListadoAlbumEvent()
}