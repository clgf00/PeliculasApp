package com.example.claudiagalerapract2.ui.listado.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.modelo.Album
import com.example.claudiagalerapract2.domain.usecases.albums.AddAlbum
import com.example.claudiagalerapract2.domain.usecases.albums.DeleteAlbum
import com.example.claudiagalerapract2.domain.usecases.albums.GetAlbums
import com.example.claudiagalerapract2.domain.usecases.albums.UpdateAlbum
import com.example.claudiagalerapract2.ui.listado.events.ListadoAlbumEvent
import com.example.claudiagalerapract2.ui.pantallamain.state.ListadoStateAlbum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListadoAlbumViewModel @Inject constructor(
    private val getAlbums: GetAlbums,
    private val addAlbum: AddAlbum,
    private val deleteAlbum: DeleteAlbum,
    private val updateAlbum: UpdateAlbum,
) : ViewModel() {

    private val _uiState = MutableLiveData(ListadoStateAlbum())
    val uiState: LiveData<ListadoStateAlbum> get() = _uiState

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
                    _uiState.value = ListadoStateAlbum(albums = emptyList())
                }

                is NetworkResult.Loading -> {
                    _uiState.value = ListadoStateAlbum(albums = emptyList())
                }
            }
        }
    }

    private fun agregarAlbum(newAlbumContent: Album) {
        viewModelScope.launch {
            when (addAlbum(newAlbumContent)) {
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

    private fun actualizarAlbum(albumId: Int, updatedAlbum: Album) {
        viewModelScope.launch {
            when (updateAlbum(albumId, updatedAlbum)) {
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


    fun handleEvent(event: ListadoAlbumEvent) {
        when (event) {
            is ListadoAlbumEvent.GetAlbums -> obtenerAlbums()
            is ListadoAlbumEvent.AddAlbum -> {
                val newAlbum = Album(userId = 0, id = 0, title = event.newAlbumContent)
                agregarAlbum(newAlbum)
            }

            is ListadoAlbumEvent.DeleteAlbum -> eliminarAlbum(event.albumId)
            is ListadoAlbumEvent.UpdateAlbum -> {
                val updatedAlbum =
                    Album(userId = 0, id = event.albumId, title = event.updatedContent)
                actualizarAlbum(event.albumId, updatedAlbum)
            }
        }
    }
}