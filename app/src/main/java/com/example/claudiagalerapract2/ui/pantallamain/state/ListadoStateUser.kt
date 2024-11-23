package com.example.claudiagalerapract2.ui.pantallamain.state

import com.example.claudiagalerapract2.domain.modelo.User


data class ListadoStateUser(
    val users: List<User> = emptyList(),
)