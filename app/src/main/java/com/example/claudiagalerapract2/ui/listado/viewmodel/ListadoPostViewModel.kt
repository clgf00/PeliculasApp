package com.example.claudiagalerapract2.ui.listado.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.modelo.Post
import com.example.claudiagalerapract2.domain.usecases.posts.AddPost
import com.example.claudiagalerapract2.domain.usecases.posts.DeletePost
import com.example.claudiagalerapract2.domain.usecases.posts.GetPosts
import com.example.claudiagalerapract2.domain.usecases.posts.UpdatePost
import com.example.claudiagalerapract2.ui.listado.events.ListadoPostEvent
import com.example.claudiagalerapract2.ui.pantallamain.state.ListadoStatePost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListadoPostViewModel @Inject constructor(
    private val getPosts: GetPosts,
    private val addPost: AddPost,
    private val deletePost: DeletePost,
    private val updatePost: UpdatePost
) : ViewModel() {

    private val _uiState = MutableLiveData(ListadoStatePost())
    val uiState: LiveData<ListadoStatePost> get() = _uiState

    init {
        handleEvent(ListadoPostEvent.GetPosts)
    }

    private fun obtenerPosts() {
        viewModelScope.launch {
            when (val result = getPosts()) {
                is NetworkResult.Success -> {
                    val users = result.data ?: emptyList()
                    _uiState.value = ListadoStatePost(posts = users)

                }

                is NetworkResult.Error -> {
                    _uiState.value = ListadoStatePost(posts = emptyList())
                }

                is NetworkResult.Loading -> {
                    _uiState.value = ListadoStatePost(posts = emptyList())
                }
            }
        }
    }
    private fun agregarPost(newPostContent: String) {
        viewModelScope.launch {
            val newPost = Post(
                userId = 0,
                title = newPostContent,
                body = newPostContent
            )
            when (addPost(newPost)) {
                is NetworkResult.Success -> {
                    obtenerPosts()
                }
                is NetworkResult.Error -> {
                    _uiState.value = ListadoStatePost(posts = emptyList())
                }
                is NetworkResult.Loading -> {
                    _uiState.value = ListadoStatePost(posts = emptyList())
                }
            }
        }
    }

    private fun eliminarPost(postId: Int) {
        viewModelScope.launch {
            when (deletePost(postId)) {
                is NetworkResult.Success -> {
                    obtenerPosts()
                }
                is NetworkResult.Error -> {
                    _uiState.value = ListadoStatePost(posts = emptyList())
                }
                is NetworkResult.Loading -> {
                    _uiState.value = ListadoStatePost(posts = emptyList())
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
                    _uiState.value = ListadoStatePost(posts = emptyList())
                }
                is NetworkResult.Loading -> {
                    _uiState.value = ListadoStatePost(posts = emptyList())
                }
            }
        }
    }


    fun handleEvent(event: ListadoPostEvent) {
        when (event) {
            is ListadoPostEvent.GetPosts -> obtenerPosts()
            is ListadoPostEvent.AddPost -> agregarPost(event.newPostContent)
            is ListadoPostEvent.DeletePost -> eliminarPost(event.postId)
            is ListadoPostEvent.UpdatePost -> actualizarPost(event.postId, event.updatedContent)
        }
    }
}