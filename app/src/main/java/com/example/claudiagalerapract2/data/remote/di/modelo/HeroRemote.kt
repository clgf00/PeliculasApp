package com.example.claudiagalerapract2.data.remote.di.modelo

import com.example.claudiagalerapract2.domain.modelo.Hero


data class HeroRemote(
    val key : String,
    val name : String,
    val portrait: String,
    val description: String,
    val role: String,
    val age : String
)

fun HeroRemote.toHero() =
    Hero(
        key = key,
        name = name,
        portrait = portrait,
        role = role,
        age = age
    )
fun HeroRemote.toHeroDetail() =
    Hero(
        name = name,
        portrait = portrait,
        description = description,
        role = role,
        age = age
    )