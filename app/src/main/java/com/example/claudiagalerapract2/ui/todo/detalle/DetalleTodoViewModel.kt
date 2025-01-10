package com.example.claudiagalerapract2.ui.todo.detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.usecases.todos.DeleteTodo
import com.example.claudiagalerapract2.domain.usecases.todos.GetTodo
import com.example.claudiagalerapract2.domain.usecases.todos.UpdateTodo
import com.example.claudiagalerapract2.ui.common.Constantes
import com.example.claudiagalerapract2.ui.todo.listado.ListadoTodoEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
            _uiState.update { it.copy(mensaje = Constantes.CARGANDO, isLoading = true, error = "") }

            when (val result = getTodo(id)) {
                is NetworkResult.Success -> {
                    _uiState.update { it.copy(todo = result.data, isLoading = false) }
                }

                is NetworkResult.Error -> {
                    _uiState.update { it.copy(error = result.message, isLoading = false) }
                }

                is NetworkResult.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun actualizarTodo(id: Int, todo: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(mensaje = Constantes.CARGANDO, isLoading = true, error = "") }

            when (val resultado = updateTodo(id, todo)) {
                is NetworkResult.Success -> {
                    _uiState.update {
                        it.copy(mensaje = Constantes.ACTUALIZADO_EXITO, todo = resultado.data, isLoading = false,)
                    }
                }

                is NetworkResult.Error -> {
                    _uiState.update {
                        it.copy(error = Constantes.ERROR, isLoading = false)
                    }
                }

                is NetworkResult.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }


    fun eliminarTodo(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(mensaje = Constantes.CARGANDO, isLoading = true, error = "") }
            when (val result = deleteTodo(id)) {
                is NetworkResult.Success -> {
                    _uiState.update {
                        it.copy(mensaje = Constantes.ELIMINADO, isLoading = false)}
                }
                is NetworkResult.Error -> {
                    _uiState.update { it.copy(error = result.message, isLoading = false) }
                }
                is NetworkResult.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }

                }
            }
        }
    }

    fun handleEvent(event: DetalleTodoEvent) {
        when (event) {
            is DetalleTodoEvent.DeleteTodo -> eliminarTodo(event.todoId)
            is DetalleTodoEvent.UpdateTodo -> {
                actualizarTodo(event.todoId, event.updatedContent)

            }
        }
    }
}

