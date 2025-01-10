package com.example.claudiagalerapract2.ui.post.detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.modelo.Post
import com.example.claudiagalerapract2.domain.usecases.comments.GetPostComments
import com.example.claudiagalerapract2.domain.usecases.posts.DeletePost
import com.example.claudiagalerapract2.domain.usecases.posts.GetPost
import com.example.claudiagalerapract2.domain.usecases.posts.GetPosts
import com.example.claudiagalerapract2.domain.usecases.posts.UpdatePost
import com.example.claudiagalerapract2.ui.common.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetallePostViewModel @Inject constructor(
    private val getPosts: GetPosts,
    private val getPost: GetPost,
    private val deletePost: DeletePost,
    private val updatePost: UpdatePost,
    private val getPostComments: GetPostComments,

    ) : ViewModel() {

    private val _uiState = MutableStateFlow(DetallePostState())
    val uiState: StateFlow<DetallePostState> get() = _uiState.asStateFlow()

    init {
        handleEvent(DetallePostEvent.GetComments)
    }

    fun errorMostrado() {
        _uiState.value = _uiState.value.copy(mensaje = null)
    }

    private fun filtrarComentarios(query: String) {
        val comentariosFiltrados = _uiState.value.comments.filter {
            it.name.contains(query, ignoreCase = true)
        }

        _uiState.value = _uiState.value.copy(
            comments = comentariosFiltrados,
        )
    }

    private fun obtenerPosts() {
        viewModelScope.launch {
            getPosts().collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.update {
                            it.copy(posts = result.data, isLoading = false)
                        }
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

    private fun actualizarPost(postId: Int, updatedContent: String) {
        viewModelScope.launch {
            val updatedPost = Post(
                id = postId, title = updatedContent, body = updatedContent
            )
            when (updatePost(postId, updatedPost)) {
                is NetworkResult.Success -> {
                    obtenerPosts()
                    _uiState.update {
                        it.copy(mensaje = Constantes.ACTUALIZADO_EXITO, isLoading = false)
                    }
                }

                is NetworkResult.Error -> {
                    _uiState.update { it.copy(error = Constantes.ERROR, isLoading = false) }
                }

                is NetworkResult.Loading -> {
                    _uiState.update { it.copy(isLoading = true, mensaje = Constantes.CARGANDO)}
                }
            }
        }
    }


    fun cambiarPost(id: Int) {
        viewModelScope.launch {
            when (val result = getPost(id)) {
                is NetworkResult.Success -> {
                    _uiState.update { it.copy(post = result.data, isLoading = false) }
                    obtenerComentarios(id)
                }

                is NetworkResult.Error -> {
                    _uiState.update { it.copy(error = Constantes.ERROR, isLoading = false) }
                }

                is NetworkResult.Loading -> {
                    _uiState.update { it.copy(isLoading = true, mensaje = Constantes.CARGANDO)}
                }
            }
        }
    }

    private fun obtenerComentarios(postId: Int) {
        viewModelScope.launch {
            try {
                val comentarios = getPostComments(postId)
                _uiState.update { it.copy(comments = comentarios) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = Constantes.ERROR) }
            }
        }
    }


    fun eliminarPost(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(mensaje = Constantes.ELIMINANDO) }
            when (val result = deletePost(id)) {
                is NetworkResult.Success -> {
                    _uiState.update { it.copy(result.data.toString(), isLoading = false, mensaje = Constantes.ELIMINADO) }
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

    fun handleEvent(event: DetallePostEvent) {
        when (event) {
            is DetallePostEvent.DeletePost -> eliminarPost(event.postId)
            is DetallePostEvent.FilterComments -> filtrarComentarios(event.query)
            DetallePostEvent.GetComments -> getPostComments
            is DetallePostEvent.UpdatePost -> actualizarPost(event.postId, event.updatedContent)
        }
    }
}
