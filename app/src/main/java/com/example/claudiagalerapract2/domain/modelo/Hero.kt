package com.example.claudiagalerapract2.domain.modelo

data class Hero(
    val key: String = "",
    val name: String = "",
    var portrait: String = "",
    val role: String = "",
    val description: String? = "",
    val age: String? = ""
)

