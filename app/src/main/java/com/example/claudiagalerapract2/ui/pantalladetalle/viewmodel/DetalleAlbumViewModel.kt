package com.example.claudiagalerapract2.ui.pantalladetalle.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.apiServices.PhotoService
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.data.remote.di.modelo.PhotoRemote
import com.example.claudiagalerapract2.data.remote.di.modelo.toPhoto
import com.example.claudiagalerapract2.domain.modelo.Album
import com.example.claudiagalerapract2.domain.modelo.Photo
import com.example.claudiagalerapract2.domain.usecases.albums.AddAlbum
import com.example.claudiagalerapract2.domain.usecases.albums.DeleteAlbum
import com.example.claudiagalerapract2.domain.usecases.albums.GetAlbum
import com.example.claudiagalerapract2.domain.usecases.albums.UpdateAlbum
import com.example.claudiagalerapract2.ui.common.Constantes
import com.example.claudiagalerapract2.ui.pantalladetalle.state.DetalleAlbumState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalleAlbumViewModel @Inject constructor(
    private val getAlbum: GetAlbum,
    private val addAlbum: AddAlbum,
    private val updateAlbum: UpdateAlbum,
    private val deleteAlbum: DeleteAlbum,
    private val photoService: PhotoService
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
                    loadPhotosForAlbum(id)

                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value?.copy(mensaje = Constantes.ERROR)
                }
                is NetworkResult.Loading -> {
                    _uiState.value = _uiState.value?.copy(mensaje = Constantes.CARGANDO)
                }
            }
        }
    }

    private fun loadPhotosForAlbum(albumId: Int) {
        viewModelScope.launch {
            val response = photoService.getPhotosForAlbum(albumId)

            if (response.isSuccessful) {
                val photos = response.body()?.map { it.toPhoto() } ?: emptyList()

                _uiState.value = _uiState.value?.copy(photos = photos)
            } else {
                _uiState.value = _uiState.value?.copy(mensaje = Constantes.ERROR)
            }
        }
    }


    fun agregarAlbum(album: Album) {
        viewModelScope.launch {
            _uiState.value = _uiState.value?.copy(mensaje = Constantes.ANYADIENDO)
            when (val result = addAlbum(album)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(
                        mensaje = Constantes.ANYADIDO_EXITO,
                        album = result.data
                    )
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value?.copy(mensaje = Constantes.ERROR)
                }
                is NetworkResult.Loading -> {
                }
            }
        }
    }

    suspend fun getPhotosForAlbum(albumId: Int): NetworkResult<List<PhotoRemote>> {
        return try {
            val response = photoService.getPhotosForAlbum(albumId)
            if (response.isSuccessful) {
                NetworkResult.Success(response.body() ?: emptyList())
            } else {
                NetworkResult.Error(Constantes.ERROR)
            }
        } catch (e: Exception) {
            NetworkResult.Error(Constantes.ERROR)
        }
    }
    fun actualizarAlbum(id: Int, album: Album) {
        viewModelScope.launch {
            _uiState.value = _uiState.value?.copy(mensaje = Constantes.ACTUALIZANDO)
            when (val result = updateAlbum(id, album)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(
                        mensaje = Constantes.ACTUALIZADO_EXITO,
                        album = result.data
                    )
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value?.copy(mensaje = Constantes.ERROR)
                }
                is NetworkResult.Loading -> {

                }
            }
        }
    }

    fun eliminarAlbum(id: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value?.copy(mensaje = Constantes.ELIMINANDO)
            when (val result = deleteAlbum(id)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(mensaje = Constantes.ELIMINADO)
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value?.copy(mensaje = Constantes.ERROR)
                }
                is NetworkResult.Loading -> {
                }
            }
        }
    }
}
