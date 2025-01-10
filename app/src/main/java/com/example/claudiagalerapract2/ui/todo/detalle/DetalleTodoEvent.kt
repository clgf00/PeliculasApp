package com.example.claudiagalerapract2.ui.todo.detalle

sealed class DetalleTodoEvent {
    data class DeleteTodo(val todoId: Int) : DetalleTodoEvent()
    data class UpdateTodo(val todoId: Int, val updatedContent: String) : DetalleTodoEvent()
}