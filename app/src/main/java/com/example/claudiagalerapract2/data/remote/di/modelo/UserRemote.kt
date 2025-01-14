package com.example.claudiagalerapract2.data.remote.di.modelo

import com.example.claudiagalerapract2.domain.modelo.User


data class UserRemote(
    val id : Int,
    val name : String,
    val username : String,
    val email: String,
    val phone : String
)

fun UserRemote.toUser() =
    User(
        id = id,
        name = name,
        email = email,
        phone = phone
    )
fun UserRemote.toUserDetail() =
    User(
        id = id,
        name = name,
        username = name,
        email = email,
        phone = phone
    )