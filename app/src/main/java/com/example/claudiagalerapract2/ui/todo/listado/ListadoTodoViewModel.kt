package com.example.claudiagalerapract2.ui.todo.listado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.modelo.Todo
import com.example.claudiagalerapract2.domain.usecases.todos.DeleteTodo
import com.example.claudiagalerapract2.domain.usecases.todos.GetTodos
import com.example.claudiagalerapract2.domain.usecases.todos.UpdateTodo
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
class ListadoTodoViewModel @Inject constructor(
    private val getTodos: GetTodos,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListadoStateTodo())
    val uiState: StateFlow<ListadoStateTodo> get() = _uiState.asStateFlow()

    init {
        handleEvent(ListadoTodoEvent.GetTodos)
    }

    private fun obtenerTodos() {
        viewModelScope.launch {
            getTodos().collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.update { it.copy(result.data, isLoading = false) }
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
    }


    private fun filtrarTodos(query: String) {
        viewModelScope.launch {
            getTodos().collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        val allTodos = result.data ?: emptyList()
                        val filteredTodos = if (query.isEmpty()) {
                            allTodos
                        } else {
                            allTodos.filter { it.title.contains(query, ignoreCase = true) }
                        }
                        _uiState.value = ListadoStateTodo(todos = filteredTodos)
                    }
                    is NetworkResult.Error -> {
                        _uiState.value = ListadoStateTodo(
                            error = result.message ?: Constantes.ERROR,
                            todos = emptyList()
                        )
                    }
                    is NetworkResult.Loading -> {
                        _uiState.value = ListadoStateTodo(isLoading = true)
                    }
                }
            }
        }
    }


    fun handleEvent(event: ListadoTodoEvent) {
        when (event) {
            is ListadoTodoEvent.GetTodos -> obtenerTodos()
            is ListadoTodoEvent.FilterTodos -> filtrarTodos(event.query)
        }
    }
}