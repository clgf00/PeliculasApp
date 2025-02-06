package com.example.claudiagalerapract2.domain.usecases.photos

import com.example.claudiagalerapract2.data.PhotoRepository
import javax.inject.Inject

class GetAlbumPhotos @Inject constructor(private val photoRepository: PhotoRepository) {
    suspend operator fun invoke(key: Int) = photoRepository.fetchAlbumPhotos(key)
}