package com.example.claudiagalerapract2.ui.listado.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.usecases.posts.GetPosts
import com.example.claudiagalerapract2.ui.listado.events.ListadoPostEvent
import com.example.claudiagalerapract2.ui.pantallamain.state.ListadoStatePost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListadoPostViewModel @Inject constructor(
    private val getPosts: GetPosts
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

    fun handleEvent(event: ListadoPostEvent) {
        when (event) {
            is ListadoPostEvent.GetPosts -> obtenerPosts()

        }
    }
}