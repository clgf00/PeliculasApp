package com.example.claudiagalerapract2.ui.comment.listado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.modelo.Comment
import com.example.claudiagalerapract2.domain.usecases.comments.DeleteComment
import com.example.claudiagalerapract2.domain.usecases.comments.GetComments
import com.example.claudiagalerapract2.domain.usecases.comments.UpdateComment
import com.example.claudiagalerapract2.ui.album.listado.ListadoStateAlbum
import com.example.claudiagalerapract2.ui.common.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
            getComments().collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.update {
                            it.copy(
                                comments = result.data,
                                isLoading = false
                            )
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


    fun handleEvent(event: ListadoCommentEvent) {
        when (event) {
            is ListadoCommentEvent.GetComments -> obtenerComments()
            is ListadoCommentEvent.FilterComments -> filtrarComments(event.query)
        }
    }
}