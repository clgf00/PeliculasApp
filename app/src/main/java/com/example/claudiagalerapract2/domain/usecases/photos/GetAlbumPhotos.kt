package com.example.claudiagalerapract2.domain.usecases.photos

import com.example.claudiagalerapract2.data.PhotoRepository
import com.example.claudiagalerapract2.domain.modelo.Photo
import javax.inject.Inject

class GetAlbumPhotos @Inject constructor(
    private val photoRepository: PhotoRepository
) {
    suspend operator fun invoke(albumId: Int): List<Photo> {
        return photoRepository.fetchPhotosByAlbums(albumId)
    }
}