package com.example.claudiagalerapract2.ui.comment.detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.modelo.Comment
import com.example.claudiagalerapract2.domain.usecases.comments.AddComment
import com.example.claudiagalerapract2.domain.usecases.comments.DeleteComment
import com.example.claudiagalerapract2.domain.usecases.comments.GetComment
import com.example.claudiagalerapract2.domain.usecases.comments.GetComments
import com.example.claudiagalerapract2.domain.usecases.comments.UpdateComment
import com.example.claudiagalerapract2.ui.common.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalleCommentViewModel @Inject constructor(
    private val getComment: GetComment,
    private val getComments: GetComments,
    private val deleteComment: DeleteComment,
    private val addComment: AddComment,
    private val updateComment: UpdateComment
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetalleCommentState())
    val uiState: StateFlow<DetalleCommentState> get() = _uiState.asStateFlow()

    fun errorMostrado() {
        _uiState.value = _uiState.value.copy(mensaje = null)
    }

    private fun agregarComment(comment: Comment) {
        viewModelScope.launch {
            when (addComment(comment)) {
                is NetworkResult.Success -> {
                    obtenerComments()
                }

                is NetworkResult.Error -> {
                    _uiState.value = DetalleCommentState(comments = emptyList())
                }

                is NetworkResult.Loading -> {
                    _uiState.value = DetalleCommentState(comments = emptyList())
                }
            }
        }
    }

    private fun obtenerComments() {
        viewModelScope.launch {
            when (val result = getComments()) {
                is NetworkResult.Success -> {
                    val comments = result.data ?: emptyList()
                    _uiState.value = DetalleCommentState(comments = comments)

                }

                is NetworkResult.Error -> {
                    _uiState.value = DetalleCommentState(comments = emptyList())
                }

                is NetworkResult.Loading -> {
                    _uiState.value = DetalleCommentState(comments = emptyList())
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
                    _uiState.value = DetalleCommentState(comments = emptyList())
                }

                is NetworkResult.Loading -> {
                    _uiState.value = DetalleCommentState(comments = emptyList())
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
                    _uiState.value = DetalleCommentState(comments = emptyList())
                }

                is NetworkResult.Loading -> {
                    _uiState.value = DetalleCommentState(comments = emptyList())
                }
            }
        }
    }

    private fun filtrarComments(query: String) {
        viewModelScope.launch {
            val allComments = getComments().let {
                if (it is NetworkResult.Success) it.data ?: emptyList() else emptyList()
            }
            val filteredComments = if (query.isEmpty()) {
                allComments
            } else {
                allComments.filter { it.name.contains(query, ignoreCase = true) }
            }

            _uiState.value = DetalleCommentState(comments = filteredComments)
        }
    }


    fun cambiarComment(id: Int) {
        viewModelScope.launch {
            when (val result = getComment(id)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value.copy(comment = result.data)
                }

                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value.copy(mensaje = Constantes.ERROR)
                }

                is NetworkResult.Loading -> {
                    _uiState.value = _uiState.value.copy(mensaje = Constantes.CARGANDO)
                }
            }
        }
    }

    fun handleEvent(event: DetalleCommentEvent) {
        when (event) {
            is DetalleCommentEvent.AddComment -> {
                val name = ""
                val email = ""

                val newComment = Comment(
                    name = name,
                    email = email,
                    body = event.newCommentContent
                )
                agregarComment(newComment)
            }
            is DetalleCommentEvent.DeleteComment -> eliminarComment(event.commentId)
            is DetalleCommentEvent.UpdateComment -> {
                val updatedComment = Comment(
                    postId = 0,
                    id = event.commentId,
                    name = "",
                    email = "",
                    body = event.updatedContent //
                )
                actualizarComment(event.commentId, updatedComment)
            }

            DetalleCommentEvent.GetComments -> obtenerComments()
            is DetalleCommentEvent.FilterComments -> filtrarComments(event.query)
        }
    }
}
