package com.example.claudiagalerapract2.ui.todo.listado

sealed class ListadoTodoEvent {
    data object GetTodos : ListadoTodoEvent()
    data class FilterTodos(val query: String) : ListadoTodoEvent()

}