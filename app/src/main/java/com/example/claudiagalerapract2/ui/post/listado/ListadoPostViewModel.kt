package com.example.claudiagalerapract2.ui.post.listado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.usecases.posts.GetPosts
import com.example.claudiagalerapract2.ui.common.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListadoPostViewModel @Inject constructor(
    private val getPosts: GetPosts,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListadoStatePost())
    val uiState: StateFlow<ListadoStatePost> get() = _uiState.asStateFlow()

    init {
        handleEvent(ListadoPostEvent.GetPosts)
    }


    fun filtrarPosts(query: String) {
        viewModelScope.launch {
            getPosts().collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        val allPosts = result.data ?: emptyList()
                        val filteredPosts = if (query.isEmpty()) {
                            allPosts
                        } else {
                            allPosts.filter { it.title.contains(query, ignoreCase = true) }
                        }
                        _uiState.update { it.copy(posts = filteredPosts) }
                    }
                    is NetworkResult.Error -> {
                        _uiState.update { it.copy(error = result.message ?: Constantes.ERROR, posts = emptyList()) }
                    }
                    is NetworkResult.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    private fun obtenerPosts() {
        viewModelScope.launch {
            getPosts().collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.update { it.copy(result.data, isLoading = false) }
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

    fun handleEvent(event: ListadoPostEvent) {
        when (event) {
            is ListadoPostEvent.FilterPosts -> filtrarPosts(event.query)
            ListadoPostEvent.GetPosts -> obtenerPosts()
        }
    }
}