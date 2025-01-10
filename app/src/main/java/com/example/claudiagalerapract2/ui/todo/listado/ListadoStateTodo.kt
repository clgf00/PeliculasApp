package com.example.claudiagalerapract2.ui.todo.listado

import com.example.claudiagalerapract2.domain.modelo.Todo

data class ListadoStateTodo (
    val todos : List<Todo> = emptyList(),
    val error : String = "",
    val mensaje : String = "",
    val isLoading : Boolean = true
)