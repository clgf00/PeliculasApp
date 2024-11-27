package com.example.claudiagalerapract2.domain.usecases.albums

import com.example.claudiagalerapract2.data.AlbumRepository
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.modelo.Album
import javax.inject.Inject

class GetAlbum @Inject constructor(private val repository: AlbumRepository) {
    suspend operator fun invoke(int: Int): NetworkResult<Album> {
        return repository.fetchAlbum(int)
    }
}