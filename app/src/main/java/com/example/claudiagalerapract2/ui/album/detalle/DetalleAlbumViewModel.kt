package com.example.claudiagalerapract2.ui.album.detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.usecases.albums.DeleteAlbum
import com.example.claudiagalerapract2.domain.usecases.albums.GetAlbum
import com.example.claudiagalerapract2.domain.usecases.albums.GetAlbums
import com.example.claudiagalerapract2.domain.usecases.photos.GetAlbumPhotos
import com.example.claudiagalerapract2.ui.common.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalleAlbumViewModel @Inject constructor(
    private val getAlbum: GetAlbum,
    private val getAlbums: GetAlbums,
    private val getAlbumPhotos : GetAlbumPhotos,
    private val deleteAlbum: DeleteAlbum,

    ) : ViewModel() {

    private val _uiState = MutableStateFlow(DetalleAlbumState())
    val uiState: StateFlow<DetalleAlbumState> get() = _uiState.asStateFlow()

    fun errorMostrado() {
        _uiState.value = _uiState.value.copy(mensaje = null)
    }

    private fun obtenerPhotos(albumId: Int) {
        viewModelScope.launch {
            try {
                val photos = getAlbumPhotos(albumId)
                _uiState.value = _uiState.value.copy(photos = photos)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(mensaje = Constantes.ERROR)
            }
        }
    }

    fun cambiarAlbum(id: Int) {
        viewModelScope.launch {
            when (val result = getAlbum(id)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value.copy(album = result.data)
                    obtenerPhotos(id)
                }

                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value.copy(mensaje = Constantes.ERROR)
                }

                is NetworkResult.Loading -> {
                    _uiState.value = _uiState.value.copy(mensaje = Constantes.CARGANDO)
                }
            }
        }
    }

    private fun filtrarPhotos(query: String) {
        viewModelScope.launch {
            val photosFiltradas = _uiState.value.photos.filter {
                it.title.contains(query, ignoreCase = true) }
            _uiState.value = _uiState.value.copy(photos = photosFiltradas)
        }
    }


    fun eliminarAlbum(id: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(mensaje = Constantes.ELIMINANDO)
            when (deleteAlbum(id)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value.copy(mensaje = Constantes.ELIMINADO)
                }

                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value.copy(mensaje = Constantes.ERROR)
                }

                is NetworkResult.Loading -> {
                }
            }
        }
    }

    fun handleEvent(event: DetalleAlbumEvent) {
        when (event) {
            is DetalleAlbumEvent.DeleteAlbum -> eliminarAlbum(event.albumId)
            is DetalleAlbumEvent.FilterPhotos -> filtrarPhotos(event.query)
            DetalleAlbumEvent.GetAlbums -> getAlbums
        }
    }
}
