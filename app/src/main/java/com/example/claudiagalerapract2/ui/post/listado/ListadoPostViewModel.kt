package com.example.claudiagalerapract2.ui.post.listado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.usecases.posts.DeletePost
import com.example.claudiagalerapract2.domain.usecases.posts.GetPosts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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



    private fun filtrarPosts(query: String) {
        viewModelScope.launch {
            val allPosts = getPosts().let {
                if (it is NetworkResult.Success) it.data ?: emptyList() else emptyList()
            }
            val filteredPosts = if (query.isEmpty()) {
                allPosts
            } else {
                allPosts.filter { it.title.contains(query, ignoreCase = true) }
            }

            _uiState.value = ListadoStatePost(posts = filteredPosts)
        }
    }



    fun handleEvent(event:ListadoPostEvent) {
        when (event) {
            is ListadoPostEvent.FilterPosts -> filtrarPosts(event.query)
            ListadoPostEvent.GetPosts -> getPosts
        }
    }
}