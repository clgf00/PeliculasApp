package com.example.claudiagalerapract2.domain.usecases.todos

import com.example.claudiagalerapract2.data.TodoRepository
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.modelo.Todo
import com.example.claudiagalerapract2.domain.modelo.User
import javax.inject.Inject

class GetTodo @Inject constructor(private val repository: TodoRepository) {
    suspend operator fun invoke(key: Int): NetworkResult<Todo> {
        return repository.fetchTodo(key)
    }
}