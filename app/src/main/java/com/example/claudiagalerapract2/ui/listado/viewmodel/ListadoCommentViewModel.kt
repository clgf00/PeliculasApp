package com.example.claudiagalerapract2.ui.listado.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.usecases.comments.GetComments
import com.example.claudiagalerapract2.ui.listado.events.ListadoCommentEvent
import com.example.claudiagalerapract2.ui.pantallamain.state.ListadoStateComment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListadoCommentViewModel @Inject constructor(
    private val getComments: GetComments
) : ViewModel() {

    private val _uiState = MutableLiveData(ListadoStateComment())
    val uiState: LiveData<ListadoStateComment> get() = _uiState

    init {
        handleEvent(ListadoCommentEvent.GetComments)
    }

    private fun obtenerComments() {
        viewModelScope.launch {
            when (val result = getComments()) {
                is NetworkResult.Success -> {
                    val users = result.data ?: emptyList()
                    _uiState.value = ListadoStateComment(comments = users)

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

    fun handleEvent(event: ListadoCommentEvent) {
        when (event) {
            is ListadoCommentEvent.GetComments -> obtenerComments()

        }
    }
}