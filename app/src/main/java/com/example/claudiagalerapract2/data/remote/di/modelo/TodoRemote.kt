package com.example.claudiagalerapract2.data.remote.di.modelo

import com.example.claudiagalerapract2.domain.modelo.Todo

data class TodoRemote (
    val userId : Int,
    val id : Int,
    val title : String,
    val completed: Boolean,
)

fun TodoRemote.toTodo() =
    Todo(
        userId = userId,
        id = id,
        title = title,
        completed = completed
    )
fun TodoRemote.toTodoDetail() =
    Todo(
        userId = userId,
        id = id,
        title = title,
        completed = completed
    )
