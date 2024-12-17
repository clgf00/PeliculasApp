package com.example.claudiagalerapract2.data

import com.example.claudiagalerapract2.data.remote.apiServices.AlbumService
import com.example.claudiagalerapract2.data.remote.di.dataSource.GalleryRemoteDataSource
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.data.remote.di.modelo.toAlbumDetail
import com.example.claudiagalerapract2.domain.modelo.Album
import javax.inject.Inject

class AlbumRepository@Inject constructor(
    private val albumService: AlbumService,
    private val galleryRemoteDataSource: GalleryRemoteDataSource,

    ) {

    suspend fun fetchAlbums(): NetworkResult<List<Album>?> {
        return galleryRemoteDataSource.fetchAlbums()

    }
    suspend fun fetchAlbum(id: Int): NetworkResult<Album> {
        try {
            val response = albumService.get(id)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body.toAlbumDetail())
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    suspend fun deleteAlbum(id: Int): NetworkResult<Unit> {
        return try {
            val response = albumService.delete(id)
            if (response.isSuccessful) {
                return NetworkResult.Success(Unit)
            }
            error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage")

}

