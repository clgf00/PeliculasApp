package com.example.claudiagalerapract2.ui.pantalladetalle.state

import com.example.claudiagalerapract2.domain.modelo.Todo

data class DetalleTodoState (
    val todo: Todo? = Todo(),
    val mensaje: String? = null,
)