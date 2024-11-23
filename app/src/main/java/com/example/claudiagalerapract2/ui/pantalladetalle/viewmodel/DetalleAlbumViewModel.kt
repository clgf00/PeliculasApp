package com.example.claudiagalerapract2.ui.pantalladetalle.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.usecases.albums.GetAlbum
import com.example.claudiagalerapract2.ui.pantalladetalle.state.DetalleAlbumState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalleAlbumViewModel @Inject constructor(
    private val getAlbum: GetAlbum,
) : ViewModel() {

    private val _uiState = MutableLiveData(DetalleAlbumState())
    val uiState: LiveData<DetalleAlbumState> get() = _uiState

    fun errorMostrado() {
        _uiState.value = _uiState.value?.copy(mensaje = null)
    }

    fun cambiarAlbum(id: Int) {
        viewModelScope.launch {
            when (val result = getAlbum(id)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(album = result.data)
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value?.copy(mensaje = "Error al cargar álbum")
                }
                is NetworkResult.Loading -> {
                    _uiState.value = _uiState.value?.copy(mensaje = "Cargando álbum...")
                }
            }
        }
    }
}
