package com.example.claudiagalerapract2.ui.listado.events

import com.example.claudiagalerapract2.domain.modelo.Todo

sealed class ListadoTodoEvent {
    data object GetTodos : ListadoTodoEvent()
    data class DeleteTodo(val todoId: Int) : ListadoTodoEvent()
    data class UpdateTodo(val todoId: Int, val updatedContent: String) : ListadoTodoEvent()
    data class AddTodo(val newCommentContent: Todo) : ListadoTodoEvent()
}