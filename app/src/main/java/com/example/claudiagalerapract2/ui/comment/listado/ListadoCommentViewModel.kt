package com.example.claudiagalerapract2.ui.comment.listado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.modelo.Comment
import com.example.claudiagalerapract2.domain.usecases.comments.AddComment
import com.example.claudiagalerapract2.domain.usecases.comments.DeleteComment
import com.example.claudiagalerapract2.domain.usecases.comments.GetComments
import com.example.claudiagalerapract2.domain.usecases.comments.UpdateComment
import com.example.claudiagalerapract2.ui.album.listado.ListadoStateAlbum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListadoCommentViewModel @Inject constructor(
    private val getComments: GetComments,

) : ViewModel() {

    private val _uiState = MutableStateFlow(ListadoStateComment())
    val uiState: StateFlow<ListadoStateComment> get() = _uiState.asStateFlow()

    init {
        handleEvent(ListadoCommentEvent.GetComments)
    }

    private fun obtenerComments() {
        viewModelScope.launch {
            when (val result = getComments()) {
                is NetworkResult.Success -> {
                    val comments = result.data ?: emptyList()
                    _uiState.value = ListadoStateComment(comments = comments)

                }

                is NetworkResult.Error -> {
                    _uiState.value = ListadoStateComment(comments = emptyList())
                }

                is NetworkResult.Loading -> {
                    _uiState.value = ListadoStateComment(comments = emptyList())
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

            _uiState.value = ListadoStateComment(comments = filteredComments)
        }
    }

    fun handleEvent(event: ListadoCommentEvent) {
        when (event) {
            is ListadoCommentEvent.GetComments -> obtenerComments()
            is ListadoCommentEvent.FilterComments -> filtrarComments(event.query)
        }
    }
}