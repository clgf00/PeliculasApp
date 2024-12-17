package com.example.claudiagalerapract2.ui.todo.listado

sealed class ListadoTodoEvent {
    data object GetTodos : ListadoTodoEvent()
    data class DeleteTodo(val todoId: Int) : ListadoTodoEvent()
    data class UpdateTodo(val todoId: Int, val updatedContent: String) : ListadoTodoEvent()
    data class FilterTodos(val query: String) : ListadoTodoEvent()

}