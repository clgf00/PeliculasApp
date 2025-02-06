package com.example.claudiagalerapract2.ui.navigation

import com.example.claudiagalerapract2.ui.common.Constantes
import kotlinx.serialization.Serializable

@Serializable
object TodosDestination {
    const val route = Constantes.TODOS
}


@Serializable
object AlbumsDestination {
    const val route = Constantes.ALBUMS
}

@Serializable
object PhotosDestination {
    const val route = Constantes.PHOTOS
    const val argAlbumId = "albumId"
    fun createRoute(albumId: Int) = "$route/$albumId"
}

val listaPantallas = listOf(
    TodosDestination.route,
    AlbumsDestination.route
)