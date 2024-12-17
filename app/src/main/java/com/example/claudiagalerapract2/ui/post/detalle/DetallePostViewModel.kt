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
import com.example.claudiagalerapract2.ui.post.listado.DetallePostEvent
import com.example.claudiagalerapract2.ui.post.listado.ListadoStatePost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetallePostViewModel @Inject constructor(
    private val getPosts : GetPosts,
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
            when (val result = getPosts()) {
                is NetworkResult.Success -> {
                    val posts = result.data ?: emptyList()
                    _uiState.value = DetallePostState(posts = posts)

                }

                is NetworkResult.Error -> {
                    _uiState.value = DetallePostState(error = Constantes.ERROR)
                }

                is NetworkResult.Loading -> {
                    _uiState.value = DetallePostState(mensaje = Constantes.CARGANDO)
                }
            }
        }
    }

    private fun actualizarPost(postId: Int, updatedContent: String) {
        viewModelScope.launch {
            val updatedPost = Post(
                userId = 0,
                id = postId,
                title = updatedContent,
                body = updatedContent
            )
            when (updatePost(postId, updatedPost)) {
                is NetworkResult.Success -> {
                    obtenerPosts()
                }
                is NetworkResult.Error -> {
                    _uiState.value = DetallePostState(error = Constantes.ERROR)
                }
                is NetworkResult.Loading -> {
                    _uiState.value = DetallePostState(mensaje = Constantes.CARGANDO)
                }
            }
        }
    }




    fun cambiarPost(id: Int) {
        viewModelScope.launch {
            when (val result = getPost(id)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value.copy(post = result.data)
                    obtenerComentarios(id)
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

    private fun obtenerComentarios(postId: Int) {
        viewModelScope.launch {
            try {
                val comentarios = getPostComments(postId)
                _uiState.value = _uiState.value.copy(comments = comentarios)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(mensaje = Constantes.ERROR)
            }
        }
    }



    fun eliminarPost(id: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(mensaje = Constantes.ELIMINANDO)
            when (deletePost(id)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value.copy(mensaje = Constantes.ELIMINADO)
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value.copy(mensaje = Constantes.ERROR)
                }
                is NetworkResult.Loading -> {
                }
            }
        }
    }

    fun handleEvent(event: DetallePostEvent) {
        when (event) {
            is DetallePostEvent.DeletePost ->  eliminarPost(event.postId)
            is DetallePostEvent.FilterComments ->filtrarComentarios(event.query)
            DetallePostEvent.GetComments -> getPostComments
            is DetallePostEvent.UpdatePost -> actualizarPost(event.postId, event.updatedContent) // Pasa los parámetros necesarios
        }
    }
}
