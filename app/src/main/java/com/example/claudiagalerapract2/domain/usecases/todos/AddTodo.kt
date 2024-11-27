package com.example.claudiagalerapract2.domain.usecases.todos

import com.example.claudiagalerapract2.data.TodoRepository
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.modelo.Todo
import javax.inject.Inject

class AddTodo @Inject constructor(private val repository: TodoRepository) {
    suspend operator fun invoke(todo: Todo): NetworkResult<Todo> {
        return repository.addTodo(todo)
    }
}