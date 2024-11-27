package com.example.claudiagalerapract2.domain.usecases.albums

import com.example.claudiagalerapract2.data.AlbumRepository
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import javax.inject.Inject

class DeleteAlbum @Inject constructor(private val repository: AlbumRepository) {
    suspend operator fun invoke(id: Int): NetworkResult<Unit> {
        return repository.deleteAlbum(id)
    }
}