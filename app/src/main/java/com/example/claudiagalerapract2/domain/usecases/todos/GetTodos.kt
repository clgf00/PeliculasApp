package com.example.claudiagalerapract2.domain.usecases.todos

import com.example.claudiagalerapract2.data.TodoRepository
import com.example.claudiagalerapract2.data.UserRepository
import javax.inject.Inject

class GetTodos @Inject constructor(private val todoRepository: TodoRepository) {
    suspend operator fun invoke() = todoRepository.fetchTodoConFlow()
}