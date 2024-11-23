package com.example.claudiagalerapract2.ui.pantalladetalle.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.usecases.comments.GetComment
import com.example.claudiagalerapract2.ui.pantalladetalle.state.DetalleCommentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalleCommentViewModel @Inject constructor(
    private val getComment: GetComment,
) : ViewModel() {

    private val _uiState = MutableLiveData(DetalleCommentState())
    val uiState: LiveData<DetalleCommentState> get() = _uiState

    fun errorMostrado() {
        _uiState.value = _uiState.value?.copy(mensaje = null)
    }

    fun cambiarComment(id: Int) {
        viewModelScope.launch {
            when (val result = getComment(id)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(comment = result.data)
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value?.copy(mensaje = "Error al cargar comentario")
                }
                is NetworkResult.Loading -> {
                    _uiState.value = _uiState.value?.copy(mensaje = "Cargando comentario...")
                }
            }
        }
    }
}
