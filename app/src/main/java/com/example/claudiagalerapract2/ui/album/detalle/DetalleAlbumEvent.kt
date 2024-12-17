package com.example.claudiagalerapract2.ui.album.detalle

sealed class DetalleAlbumEvent {
    data object GetAlbums : DetalleAlbumEvent()
    data class DeleteAlbum(val albumId: Int) : DetalleAlbumEvent()
    data class FilterPhotos(val query: String) : DetalleAlbumEvent()

}