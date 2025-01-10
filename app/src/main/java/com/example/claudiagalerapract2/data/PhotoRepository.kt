package com.example.claudiagalerapract2.data

import com.example.claudiagalerapract2.data.remote.apiServices.PhotoService
import com.example.claudiagalerapract2.data.remote.di.dataSource.GalleryRemoteDataSource
import com.example.claudiagalerapract2.data.remote.di.di.IoDispatcher
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.data.remote.di.modelo.toCommentDetail
import com.example.claudiagalerapract2.data.remote.di.modelo.toPhotoDetail
import com.example.claudiagalerapract2.domain.modelo.Album
import com.example.claudiagalerapract2.domain.modelo.Comment
import com.example.claudiagalerapract2.domain.modelo.Photo
import com.example.claudiagalerapract2.ui.common.Constantes
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PhotoRepository@Inject constructor(
    private val photoService: PhotoService,
    private val galleryRemoteDataSource: GalleryRemoteDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,


    ) {

    suspend fun fetchPhotosConFlow(): Flow<NetworkResult<List<Photo>>> {
        return flow {

            emit(NetworkResult.Loading())
            val result = galleryRemoteDataSource.fetchPhotos()
            emit(result)

        }
            .catch { e ->
                emit(NetworkResult.Error(e.message ?: Constantes.ERROR))
            }
            .flowOn(dispatcher)
    }

    suspend fun fetchPhoto(id: Int): NetworkResult<Photo> {

        try {
            val response = photoService.get(id)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body.toPhotoDetail())
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    suspend fun fetchPhotosByAlbums(albumId: Int): List<Photo> {
        val response = photoService.getPhotosForAlbum(albumId)
        if (response.isSuccessful) {
            return response.body()?.map { it.toPhotoDetail() } ?: emptyList()
        } else {
            throw Exception(Constantes.ERROR_FETCH)
        }
    }

    private fun <T> error(message: String): NetworkResult<T> =
        NetworkResult.Error(message)


}