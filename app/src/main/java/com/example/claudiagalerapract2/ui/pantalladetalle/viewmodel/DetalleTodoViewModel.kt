package com.example.claudiagalerapract2.ui.pantalladetalle.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.usecases.todos.GetTodo
import com.example.claudiagalerapract2.ui.pantalladetalle.state.DetalleTodoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalleTodoViewModel @Inject constructor(
    private val getTodo: GetTodo,
) : ViewModel() {

    private val _uiState = MutableLiveData(DetalleTodoState())
    val uiState: LiveData<DetalleTodoState> get() = _uiState

    fun errorMostrado() {
        _uiState.value = _uiState.value?.copy(mensaje = null)
    }

    fun cambiarTodo(id: Int) {
        viewModelScope.launch {
            when (val result = getTodo(id)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(todo = result.data)
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value?.copy(mensaje = "Error al cargar tarea")
                }
                is NetworkResult.Loading -> {
                    _uiState.value = _uiState.value?.copy(mensaje = "Cargando tarea...")
                }
            }
        }
    }
}
