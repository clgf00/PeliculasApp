package com.example.claudiagalerapract2.ui.album.listado

sealed class ListadoAlbumEvent {
    data object GetAlbums: ListadoAlbumEvent()
    data class FilterAlbums(val query: String) : ListadoAlbumEvent()
    data class DeleteALbum(val albumId: Int) : ListadoAlbumEvent()



}