package com.example.claudiagalerapract2.domain.usecases.todos

import com.example.claudiagalerapract2.data.AlbumRepository
import com.example.claudiagalerapract2.data.TodoRepository
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import javax.inject.Inject

class DeleteTodo @Inject constructor(private val repository: TodoRepository) {
    suspend operator fun invoke(id: Int): NetworkResult<Unit> {
        return repository.deleteTodo(id)
    }
}