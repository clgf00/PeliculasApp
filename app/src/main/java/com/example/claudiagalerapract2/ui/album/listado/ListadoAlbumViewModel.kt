package com.example.claudiagalerapract2.ui.album.listado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.di.IoDispatcher
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.usecases.albums.DeleteAlbum
import com.example.claudiagalerapract2.domain.usecases.albums.GetAlbums
import com.example.claudiagalerapract2.ui.common.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListadoAlbumViewModel @Inject constructor(
    private val getAlbums: GetAlbums,
    private val deleteAlbum: DeleteAlbum,
    @IoDispatcher val dispatcher: CoroutineDispatcher

) : ViewModel() {

    private val _uiState = MutableStateFlow(ListadoStateAlbum())
    val uiState: StateFlow<ListadoStateAlbum> get() = _uiState.asStateFlow()

    init {
        handleEvent(ListadoAlbumEvent.GetAlbums)
    }
    private fun obtenerAlbums() {
        viewModelScope.launch {
            getAlbums().collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.update { it.copy(albums = result.data, isLoading = false) }
                    }
                    is NetworkResult.Error -> {
                        _uiState.update { it.copy(error = result.message ?: Constantes.ERROR, isLoading = false) }
                    }
                    is NetworkResult.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
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
                    _uiState.update { it.copy(error = Constantes.ERROR, isLoading = false) }
                }

                is NetworkResult.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }


    private fun filtrarAlbums(query: String) {
        viewModelScope.launch {
            getAlbums().collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        val allAlbums = result.data
                        val filteredAlbums = if (query.isEmpty()) {
                            allAlbums
                        } else {
                            allAlbums.filter { it.title.contains(query, ignoreCase = true) }
                        }
                        _uiState.update { it.copy(albums = filteredAlbums) }
                    }
                    is NetworkResult.Error -> {
                        _uiState.update { it.copy(error = result.message, isLoading = false) }
                    }
                    is NetworkResult.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                }
            }
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