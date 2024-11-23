package com.example.claudiagalerapract2.ui.pantalladetalle.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.usecases.photos.GetPhoto
import com.example.claudiagalerapract2.ui.pantalladetalle.state.DetallePhotoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetallePhotoViewModel @Inject constructor(
    private val getPhoto: GetPhoto,
) : ViewModel() {

    private val _uiState = MutableLiveData(DetallePhotoState())
    val uiState: LiveData<DetallePhotoState> get() = _uiState

    fun errorMostrado() {
        _uiState.value = _uiState.value?.copy(mensaje = null)
    }

    fun cambiarPhoto(id: Int) {
        viewModelScope.launch {
            when (val result = getPhoto(id)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(photo = result.data)
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value?.copy(mensaje = "Error al cargar foto")
                }
                is NetworkResult.Loading -> {
                    _uiState.value = _uiState.value?.copy(mensaje = "Cargando foto...")
                }
            }
        }
    }
}
