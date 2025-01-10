package com.example.claudiagalerapract2.domain.usecases.photos

import com.example.claudiagalerapract2.data.PhotoRepository
import javax.inject.Inject

class GetPhotos @Inject constructor(private val photoRepository: PhotoRepository) {
    suspend operator fun invoke() = photoRepository.fetchPhotosConFlow()
}