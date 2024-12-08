package com.example.claudiagalerapract2.ui.listado.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.modelo.Todo
import com.example.claudiagalerapract2.domain.usecases.todos.AddTodo
import com.example.claudiagalerapract2.domain.usecases.todos.DeleteTodo
import com.example.claudiagalerapract2.domain.usecases.todos.GetTodos
import com.example.claudiagalerapract2.domain.usecases.todos.UpdateTodo
import com.example.claudiagalerapract2.ui.listado.events.ListadoTodoEvent
import com.example.claudiagalerapract2.ui.pantallamain.state.ListadoStateTodo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListadoTodoViewModel @Inject constructor(
    private val getTodos: GetTodos,
    private val addTodo: AddTodo,
    private val deleteTodo: DeleteTodo,
    private val updateTodo: UpdateTodo
) : ViewModel() {

    private val _uiState = MutableLiveData(ListadoStateTodo())
    val uiState: LiveData<ListadoStateTodo> get() = _uiState

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

    private fun agregarTodo(newTodo: Todo) {
        viewModelScope.launch {
            when (addTodo(newTodo)) {
                is NetworkResult.Success -> {
                    obtenerTodos()
                }

                is NetworkResult.Error -> {
                    _uiState.value = ListadoStateTodo(
                        todos = emptyList(),
                    )
                }

                is NetworkResult.Loading -> {
                    _uiState.value = ListadoStateTodo(
                    )
                }
            }
        }
    }

    private fun eliminarTodo(todoId: Int) {
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

    private fun actualizarTodo(todoId: Int, updatedContent: String) {
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
            is ListadoTodoEvent.AddTodo -> agregarTodo(event.newCommentContent)
            is ListadoTodoEvent.DeleteTodo -> eliminarTodo(event.todoId)
            is ListadoTodoEvent.UpdateTodo -> actualizarTodo(
                todoId = event.todoId,
                updatedContent = event.updatedContent
            )
        }
    }
}