package com.example.claudiagalerapract2.data

import com.example.claudiagalerapract2.data.remote.apiServices.AlbumService
import com.example.claudiagalerapract2.data.remote.di.dataSource.GalleryRemoteDataSource
import com.example.claudiagalerapract2.data.remote.di.di.IoDispatcher
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.data.remote.di.modelo.toAlbumDetail
import com.example.claudiagalerapract2.domain.modelo.Album
import com.example.claudiagalerapract2.ui.common.Constantes
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AlbumRepository@Inject constructor(
    private val albumService: AlbumService,
    private val galleryRemoteDataSource: GalleryRemoteDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,


    ) {

    suspend fun fetchAlbumsConFlow(): Flow<NetworkResult<List<Album>>>{
        return flow {

            emit(NetworkResult.Loading())
            val result = galleryRemoteDataSource.fetchAlbums()
            emit(result)

        }
            .catch { e ->
                emit(NetworkResult.Error(e.message ?: Constantes.ERROR))
            }
            .flowOn(dispatcher)
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

    private fun <T> error(message: String): NetworkResult<T> =
        NetworkResult.Error(message)

}

