package com.example.claudiagalerapract2.domain.usecases.albums

import com.example.claudiagalerapract2.data.AlbumRepository
import javax.inject.Inject

class GetAlbums @Inject constructor(private val albumRepository: AlbumRepository) {
    suspend operator fun invoke() = albumRepository.fetchAlbumsConFlow()
}