package com.example.claudiagalerapract2.ui.todopantalla

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.apiServices.TodoService
import com.example.claudiagalerapract2.data.remote.di.modelo.toTodo
import com.example.claudiagalerapract2.domain.modelo.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoService: TodoService
) : ViewModel() {

    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos

    init {
        fetchTodos()
    }

    private fun fetchTodos() {
        viewModelScope.launch {
            try {
                val response = todoService.get()
                if (response.isSuccessful) {
                    _todos.value = response.body()?.map { it.toTodo() } ?: emptyList()
                } else {
                    _todos.value = emptyList()
                }
            } catch (e: Exception) {
                _todos.value = emptyList()
            }
        }
    }
}
