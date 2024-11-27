package com.example.claudiagalerapract2.ui.pantalladetalle.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.modelo.Comment
import com.example.claudiagalerapract2.domain.usecases.comments.AddComment
import com.example.claudiagalerapract2.domain.usecases.comments.DeleteComment
import com.example.claudiagalerapract2.domain.usecases.comments.GetComment
import com.example.claudiagalerapract2.domain.usecases.comments.UpdateComment
import com.example.claudiagalerapract2.ui.common.Constantes
import com.example.claudiagalerapract2.ui.pantalladetalle.state.DetalleCommentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalleCommentViewModel @Inject constructor(
    private val getComment: GetComment,
    private val addComment: AddComment,
    private val updateComment: UpdateComment,
    private val deleteComment: DeleteComment
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
                    _uiState.value = _uiState.value?.copy(mensaje = Constantes.ERROR)
                }

                is NetworkResult.Loading -> {
                    _uiState.value = _uiState.value?.copy(mensaje = Constantes.CARGANDO)
                }
            }
        }
    }

    fun agregarComment(comment: Comment) {
        viewModelScope.launch {
            _uiState.value = _uiState.value?.copy(mensaje = Constantes.ANYADIENDO)
            when (val result = addComment(comment)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(
                        mensaje = Constantes.ANYADIDO_EXITO,
                        comment = result.data
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

    fun actualizarComment(id: Int, comment: Comment) {
        viewModelScope.launch {
            _uiState.value = _uiState.value?.copy(mensaje = Constantes.ACTUALIZANDO)
            when (val result = updateComment(id, comment)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(
                        mensaje = Constantes.ACTUALIZADO_EXITO,
                        comment = result.data
                    )
                }

                is NetworkResult.Error -> {
                    _uiState.value =
                        _uiState.value?.copy(mensaje = Constantes.ERROR)
                }

                is NetworkResult.Loading -> {
                }
            }
        }
    }

    fun eliminarComment(id: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value?.copy(mensaje = Constantes.ELIMINANDO)
            when (val result = deleteComment(id)) {
                is NetworkResult.Success -> {
                    _uiState.value =
                        _uiState.value?.copy(mensaje = Constantes.ELIMINADO)
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
