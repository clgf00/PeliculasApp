package com.example.claudiagalerapract2.ui.pantalladetalle.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.modelo.Post
import com.example.claudiagalerapract2.domain.usecases.comments.GetComment
import com.example.claudiagalerapract2.domain.usecases.comments.GetComments
import com.example.claudiagalerapract2.domain.usecases.comments.GetPostComments
import com.example.claudiagalerapract2.domain.usecases.posts.AddPost
import com.example.claudiagalerapract2.domain.usecases.posts.DeletePost
import com.example.claudiagalerapract2.domain.usecases.posts.GetPost
import com.example.claudiagalerapract2.domain.usecases.posts.UpdatePost
import com.example.claudiagalerapract2.ui.common.Constantes
import com.example.claudiagalerapract2.ui.pantalladetalle.state.DetallePostState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetallePostViewModel @Inject constructor(
    private val getPost: GetPost,
    private val getComments: GetComments,
    private val addPost: AddPost,
    private val updatePost: UpdatePost,
    private val deletePost: DeletePost,
    private val getPostComments: GetPostComments,

) : ViewModel() {

    private val _uiState = MutableLiveData(DetallePostState())
    val uiState: LiveData<DetallePostState> get() = _uiState

    fun errorMostrado() {
        _uiState.value = _uiState.value?.copy(mensaje = null)
    }

    fun cambiarPost(id: Int) {
        viewModelScope.launch {
            when (val result = getPost(id)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(post = result.data)
                    obtenerComentarios(id)
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

    private fun obtenerComentarios(postId: Int) {
        viewModelScope.launch {
            try {
                val comentarios = getPostComments(postId)
                _uiState.value = _uiState.value?.copy(comments = comentarios)
            } catch (e: Exception) {
                _uiState.value = _uiState.value?.copy(mensaje = Constantes.ERROR)
            }
        }
    }

    fun agregarPost(post: Post) {
        viewModelScope.launch {
            _uiState.value = _uiState.value?.copy(mensaje = Constantes.ANYADIENDO)
            when (val result = addPost(post)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(mensaje = Constantes.ANYADIDO_EXITO, post = result.data)
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value?.copy(mensaje = Constantes.ERROR)
                }
                is NetworkResult.Loading -> {
                }
            }
        }
    }

    fun actualizarPost(id: Int, post: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value?.copy(mensaje = Constantes.ACTUALIZANDO)
            val updatedPost = Post(
                id = id,
                body = post
            )

            when (val result = updatePost(id, updatedPost)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(
                        mensaje = Constantes.ACTUALIZADO_EXITO,
                        post = result.data
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

    fun eliminarPost(id: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value?.copy(mensaje = Constantes.ELIMINANDO)
            when (deletePost(id)) {
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
