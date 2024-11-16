package com.example.claudiagalerapract2.data.remote.di

import com.example.claudiagalerapract2.domain.modelo.Hero

class Repository {
    private val heroes = mutableListOf<Hero>()
    init {
        heroes.add(Hero("a", "El Gran Escape", "Juan Perez", "accion"))
        heroes.add(Hero("e", "Viaje al Futuro","Maria Lopez", "drama"))
        heroes.add(Hero("i", "Misión Rescate", "Carlos García", "terror"))
        heroes.add(
            Hero(
                "aa",
                "La Ciudad Perdida",
                "Ana Martinez",
                "wd"
            )
        )
        heroes.add(
            Hero(
                "ee",
                "Operación Relámpago",
                "Luis Hernandez",
                "cdfsd"
            )
        )
    }

    fun addPelicula(pelicula: Hero) = heroes.add(pelicula)

    fun getPeliculas(): List<Hero> {
        return heroes.toList()
    }

    fun deletePelicula(index: Int): Boolean {
        try {
            heroes.removeAt(index)
            return true
        } catch (e: Error) {
            return false
        }
    }

    fun updatePelicula(pelicula: Hero, index: Int): Boolean {
        try {
            heroes[index] = pelicula
            return true
        } catch (e: Error) {
            return false
        }
    }
}