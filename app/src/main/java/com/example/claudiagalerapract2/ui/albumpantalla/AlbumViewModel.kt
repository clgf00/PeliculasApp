package com.example.claudiagalerapract2.ui.albumpantalla

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.apiServices.AlbumService
import com.example.claudiagalerapract2.data.remote.di.modelo.toAlbum
import com.example.claudiagalerapract2.domain.modelo.Album
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val albumService: AlbumService,
) : ViewModel() {

    private val _albums = MutableStateFlow<List<Album>>(emptyList())
    val albums: StateFlow<List<Album>> = _albums

    init {
        fetchAlbums()
    }

    private fun fetchAlbums() {
        viewModelScope.launch {
            try {
                val response = albumService.getAll()
                if (response.isSuccessful) {
                    _albums.value = response.body()?.map { it.toAlbum() } ?: emptyList()
                } else {
                    _albums.value = emptyList()
                }
            } catch (e: Exception) {
                _albums.value = emptyList()
            }
        }
    }
}
