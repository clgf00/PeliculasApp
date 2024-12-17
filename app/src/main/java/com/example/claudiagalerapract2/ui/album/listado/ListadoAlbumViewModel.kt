package com.example.claudiagalerapract2.ui.album.listado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.modelo.Album
import com.example.claudiagalerapract2.domain.usecases.albums.DeleteAlbum
import com.example.claudiagalerapract2.domain.usecases.albums.GetAlbums
import com.example.claudiagalerapract2.ui.common.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListadoAlbumViewModel @Inject constructor(
    private val getAlbums: GetAlbums,
    private val deleteAlbum: DeleteAlbum,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListadoStateAlbum())
    val uiState: StateFlow<ListadoStateAlbum> get() = _uiState.asStateFlow()

    init {
        handleEvent(ListadoAlbumEvent.GetAlbums)
    }

    private fun obtenerAlbums() {
        viewModelScope.launch {
            when (val result = getAlbums()) {
                is NetworkResult.Success -> {
                    val albums = result.data ?: emptyList()
                    _uiState.value = ListadoStateAlbum(albums = albums)

                }

                is NetworkResult.Error -> {
                    _uiState.value = ListadoStateAlbum(error = Constantes.ERROR)
                }

                is NetworkResult.Loading -> {
                    _uiState.value = ListadoStateAlbum(mensaje = Constantes.CARGANDO)
                }
            }
        }
    }


    private fun eliminarAlbum(albumId: Int) {
        viewModelScope.launch {
            when (deleteAlbum(albumId)) {
                is NetworkResult.Success -> {
                    obtenerAlbums()
                }

                is NetworkResult.Error -> {
                    _uiState.value = ListadoStateAlbum(albums = emptyList())
                }

                is NetworkResult.Loading -> {
                    _uiState.value = ListadoStateAlbum(albums = emptyList())
                }
            }
        }
    }


    private fun filtrarAlbums(query: String) {
        viewModelScope.launch {
            val allAlbums = getAlbums().let {
                if (it is NetworkResult.Success) it.data ?: emptyList() else emptyList()
            }
            val filteredAlbums = if (query.isEmpty()) {
                allAlbums
            } else {
                allAlbums.filter { it.title.contains(query, ignoreCase = true) }
            }

            _uiState.value = ListadoStateAlbum(albums = filteredAlbums)
        }
    }


    fun handleEvent(event: ListadoAlbumEvent) {
        when (event) {
            is ListadoAlbumEvent.GetAlbums -> obtenerAlbums()
            is ListadoAlbumEvent.FilterAlbums -> filtrarAlbums(event.query)
            is ListadoAlbumEvent.DeleteALbum -> eliminarAlbum(event.albumId)
        }
    }
}