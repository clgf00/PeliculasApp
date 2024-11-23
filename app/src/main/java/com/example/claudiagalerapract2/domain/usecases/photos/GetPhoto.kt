package com.example.claudiagalerapract2.domain.usecases.photos

import com.example.claudiagalerapract2.data.PhotoRepository
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.modelo.Photo
import javax.inject.Inject

class GetPhoto @Inject constructor(private val repository: PhotoRepository) {
    suspend operator fun invoke(key: Int): NetworkResult<Photo> {
        return repository.fetchPhoto(key)
    }
}