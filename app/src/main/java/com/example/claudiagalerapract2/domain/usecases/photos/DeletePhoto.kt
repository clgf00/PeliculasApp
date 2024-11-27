package com.example.claudiagalerapract2.domain.usecases.photos

import com.example.claudiagalerapract2.data.PhotoRepository
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import javax.inject.Inject

class DeletePhoto @Inject constructor(private val repository: PhotoRepository) {
    suspend operator fun invoke(id: Int): NetworkResult<Unit> {
        return repository.deletePhoto(id)
    }
}