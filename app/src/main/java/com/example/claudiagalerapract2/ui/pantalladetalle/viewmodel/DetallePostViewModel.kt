package com.example.claudiagalerapract2.ui.pantalladetalle.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.usecases.posts.GetPost
import com.example.claudiagalerapract2.ui.pantalladetalle.state.DetallePostState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetallePostViewModel @Inject constructor(
    private val getPost: GetPost,
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
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value?.copy(mensaje = "Error al cargar post")
                }
                is NetworkResult.Loading -> {
                    _uiState.value = _uiState.value?.copy(mensaje = "Cargando post...")
                }
            }
        }
    }
}
