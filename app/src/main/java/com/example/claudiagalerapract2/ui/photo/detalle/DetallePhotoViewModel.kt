package com.example.claudiagalerapract2.ui.photo.detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.usecases.photos.DeletePhoto
import com.example.claudiagalerapract2.domain.usecases.photos.GetPhoto
import com.example.claudiagalerapract2.ui.common.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetallePhotoViewModel @Inject constructor(
    private val getPhoto: GetPhoto,
    private val deletePhoto: DeletePhoto
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetallePhotoState())
    val uiState: StateFlow<DetallePhotoState> get() = _uiState.asStateFlow()

    fun errorMostrado() {
        _uiState.value = _uiState.value.copy(mensaje = null)
    }

    fun cambiarPhoto(id: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(mensaje = Constantes.CARGANDO)
            when (val result = getPhoto(id)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value.copy(photo = result.data, mensaje = null)
                }

                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value.copy(mensaje = "${Constantes.ERROR}: ${result.message}")
                }

                is NetworkResult.Loading -> {
                    _uiState.value = _uiState.value.copy(mensaje = Constantes.CARGANDO)
                }
            }
        }
    }

    fun eliminarPhoto(id: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(mensaje = Constantes.ELIMINANDO)
            when (val result = deletePhoto(id)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        mensaje = Constantes.ELIMINADO,
                        photo = null
                    )
                }

                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value.copy(mensaje = "${Constantes.ERROR}: ${result.message}")
                }

                is NetworkResult.Loading -> {
                    _uiState.value = _uiState.value.copy(mensaje = Constantes.CARGANDO)
                }
            }
        }
    }
    //handleevent
}
