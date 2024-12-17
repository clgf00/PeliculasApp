package com.example.claudiagalerapract2.ui.todo.listado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.modelo.Todo
import com.example.claudiagalerapract2.domain.usecases.todos.DeleteTodo
import com.example.claudiagalerapract2.domain.usecases.todos.GetTodos
import com.example.claudiagalerapract2.domain.usecases.todos.UpdateTodo
import com.example.claudiagalerapract2.ui.album.listado.ListadoStateAlbum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListadoTodoViewModel @Inject constructor(
    private val getTodos: GetTodos,
    private val deleteTodo: DeleteTodo,
    private val updateTodo: UpdateTodo
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListadoStateTodo())
    val uiState: StateFlow<ListadoStateTodo> get() = _uiState.asStateFlow()

    init {
        handleEvent(ListadoTodoEvent.GetTodos)
    }

    private fun obtenerTodos() {
        viewModelScope.launch {
            when (val result = getTodos()) {
                is NetworkResult.Success -> {
                    val todos = result.data ?: emptyList()
                    _uiState.value = ListadoStateTodo(todos = todos)

                }

                is NetworkResult.Error -> {
                    _uiState.value = ListadoStateTodo(todos = emptyList())
                }

                is NetworkResult.Loading -> {
                    _uiState.value = ListadoStateTodo(todos = emptyList())
                }
            }
        }
    }


    private fun filtrarTodos(query: String) {
        viewModelScope.launch {
            val allTodos = getTodos().let {
                if (it is NetworkResult.Success) it.data ?: emptyList() else emptyList()
            }
            val filteredTodos = if (query.isEmpty()) {
                allTodos
            } else {
                allTodos.filter { it.title.contains(query, ignoreCase = true) }
            }

            _uiState.value = ListadoStateTodo(todos = filteredTodos)
        }
    }

    fun eliminarTodo(todoId: Int) {
        viewModelScope.launch {
            when (deleteTodo(todoId)) {
                is NetworkResult.Success -> {
                    obtenerTodos()
                }

                is NetworkResult.Error -> {
                    _uiState.value = ListadoStateTodo(todos = emptyList())
                }

                is NetworkResult.Loading -> {
                    _uiState.value = ListadoStateTodo(todos = emptyList())
                }
            }
        }
    }

    fun actualizarTodo(todoId: Int, updatedContent: String) {
        viewModelScope.launch {
            val updatedTodo = Todo(
                userId = 0,
                id = todoId,
                title = updatedContent,
            )

            when (updateTodo(todoId, updatedTodo.toString())) {
                is NetworkResult.Success -> {
                    obtenerTodos()
                }

                is NetworkResult.Error -> {
                    _uiState.value = ListadoStateTodo(todos = emptyList())
                }

                is NetworkResult.Loading -> {
                    _uiState.value = ListadoStateTodo(todos = emptyList())
                }
            }
        }
    }

    fun handleEvent(event: ListadoTodoEvent) {
        when (event) {
            is ListadoTodoEvent.GetTodos -> obtenerTodos()
            is ListadoTodoEvent.DeleteTodo -> eliminarTodo(event.todoId)
            is ListadoTodoEvent.UpdateTodo -> actualizarTodo(
                todoId = event.todoId,
                updatedContent = event.updatedContent
            )

            is ListadoTodoEvent.FilterTodos -> filtrarTodos(event.query)
        }
    }
}