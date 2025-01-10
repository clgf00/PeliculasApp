package com.example.claudiagalerapract2.ui.comment.detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.modelo.Comment
import com.example.claudiagalerapract2.domain.usecases.comments.DeleteComment
import com.example.claudiagalerapract2.domain.usecases.comments.GetComment
import com.example.claudiagalerapract2.domain.usecases.comments.GetComments
import com.example.claudiagalerapract2.domain.usecases.comments.UpdateComment
import com.example.claudiagalerapract2.ui.common.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalleCommentViewModel @Inject constructor(
    private val getComment: GetComment,
    private val getComments: GetComments,
    private val deleteComment: DeleteComment,
    private val updateComment: UpdateComment
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetalleCommentState())
    val uiState: StateFlow<DetalleCommentState> get() = _uiState.asStateFlow()

    fun errorMostrado() {
        _uiState.value = _uiState.value.copy(mensaje = null)
    }

    private fun obtenerComments() {
        viewModelScope.launch {
            getComments().collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.update { it.copy(comments = result.data, isLoading = false) }
                    }

                    is NetworkResult.Error -> {
                        _uiState.update { it.copy(error = Constantes.ERROR, isLoading = false) }
                    }

                    is NetworkResult.Loading -> {
                        _uiState.update { it.copy(isLoading = true, mensaje = Constantes.CARGANDO) }
                    }
                }
            }
        }
    }


    private fun eliminarComment(commentId: Int) {
        viewModelScope.launch {
            when (deleteComment(commentId)) {
                is NetworkResult.Success -> {
                    obtenerComments()
                }

                is NetworkResult.Error -> {
                    _uiState.update { it.copy(error= Constantes.ERROR)}
                }

                is NetworkResult.Loading -> {
                    _uiState.update { it.copy(isLoading = true, mensaje = Constantes.CARGANDO)}
                }
            }
        }
    }

    fun actualizarComment(commentId: Int, updatedComment: Comment) {
        viewModelScope.launch {
            when (updateComment(commentId, updatedComment)) {
                is NetworkResult.Success -> {
                    obtenerComments()
                }

                is NetworkResult.Error -> {
                    _uiState.update { it.copy(error = Constantes.ERROR, isLoading = false)}
                }

                is NetworkResult.Loading -> {
                    _uiState.update { it.copy(isLoading = true, mensaje = Constantes.CARGANDO)}
                }
            }
        }
    }

    private fun filtrarComments(query: String) {
        viewModelScope.launch {
            getComments().collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        val allComments = result.data
                        val filteredComments = if (query.isEmpty()) {
                            allComments
                        } else {
                            allComments.filter { it.name.contains(query, ignoreCase = true) }
                        }
                        _uiState.update { it.copy(comments = filteredComments) }
                    }
                    is NetworkResult.Error -> {
                        _uiState.update { it.copy(error = result.message, isLoading = false) }
                    }
                    is NetworkResult.Loading -> {
                        _uiState.update { it.copy(isLoading = true, mensaje = Constantes.CARGANDO) }
                    }
                }
            }
        }
    }


    fun cambiarComment(id: Int) {
        viewModelScope.launch {
            when (val result = getComment(id)) {
                is NetworkResult.Success -> {
                    _uiState.update { it.copy(comment = result.data, isLoading = false)}
                }

                is NetworkResult.Error -> {
                    _uiState.update { it.copy(error = Constantes.ERROR, isLoading = false)}
                }

                is NetworkResult.Loading -> {
                    _uiState.update { it.copy(isLoading = true, mensaje = Constantes.CARGANDO)}
                }
            }
        }
    }

    fun handleEvent(event: DetalleCommentEvent) {
        when (event) {
            is DetalleCommentEvent.DeleteComment -> eliminarComment(event.commentId)
            is DetalleCommentEvent.UpdateComment -> {
                val updatedComment = Comment(postId = 0, id = event.commentId, name = "", email = "", body = event.updatedContent)
                actualizarComment(event.commentId, updatedComment)
            }

            DetalleCommentEvent.GetComments -> obtenerComments()
            is DetalleCommentEvent.FilterComments -> filtrarComments(event.query)
        }
    }
}
