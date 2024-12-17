package com.example.claudiagalerapract2.domain.modelo

import com.example.claudiagalerapract2.data.remote.di.modelo.Address

data class User(
    val id: Int=0,
    val name: String="",
    val username: String = "",
    var email: String = "",
    val address: Address= Address(),
    val phone : String="",

    )

