package com.example.claudiagalerapract2.data

import com.example.claudiagalerapract2.data.remote.apiServices.PhotoService
import com.example.claudiagalerapract2.data.remote.di.dataSource.GalleryRemoteDataSource
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.data.remote.di.modelo.toPhotoDetail
import com.example.claudiagalerapract2.domain.modelo.Photo
import javax.inject.Inject

class PhotoRepository@Inject constructor(
    private val photoService: PhotoService,
    private val galleryRemoteDataSource: GalleryRemoteDataSource,

    ) {

    suspend fun fetchPhotos(): NetworkResult<List<Photo>?> {
        return galleryRemoteDataSource.fetchPhotos()

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
    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage")

}