package com.example.claudiagalerapract2.ui.todo.detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.usecases.todos.DeleteTodo
import com.example.claudiagalerapract2.domain.usecases.todos.GetTodo
import com.example.claudiagalerapract2.domain.usecases.todos.UpdateTodo
import com.example.claudiagalerapract2.ui.common.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalleTodoViewModel @Inject constructor(
    private val getTodo: GetTodo,
    private val updateTodo: UpdateTodo,
    private val deleteTodo: DeleteTodo
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetalleTodoState())
    val uiState: StateFlow<DetalleTodoState> get() = _uiState.asStateFlow()

    fun errorMostrado() {
        _uiState.value = _uiState.value.copy(mensaje = null)
    }

    fun cambiarTodo(id: Int) {
        viewModelScope.launch {
            when (val result = getTodo(id)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value.copy(todo = result.data)
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

    fun actualizarTodo(id: Int, todo: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(mensaje = Constantes.ACTUALIZANDO)
            when (val result = updateTodo(id, todo)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        mensaje = Constantes.ACTUALIZADO_EXITO,
                        todo = result.data
                    )
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value.copy(mensaje = Constantes.ERROR)
                }
                is NetworkResult.Loading -> {
                }
            }
        }
    }

    fun eliminarTodo(id: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(mensaje = Constantes.ELIMINANDO)
            when (deleteTodo(id)) {
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
}

