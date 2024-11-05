package com.example.claudiagalerapract2.data

import com.example.claudiagalerapract2.domain.modelo.Pelicula
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Repository @Inject constructor() {

    private val peliculas = mutableListOf<Pelicula>()
    init {
        peliculas.add(Pelicula(0, "El Gran Escape", 1998, "Juan Perez", "accion", false, 1.0))
        peliculas.add(Pelicula(1, "Viaje al Futuro", 2002, "Maria Lopez", "drama", true, 3.0))
        peliculas.add(Pelicula(2, "La Batalla Final", 2013, "Jose Rodriguez", "comedia", true, 5.0))
        peliculas.add(Pelicula(3, "Misión Rescate", 2015, "Carlos García", "terror", true, 4.0))
        peliculas.add(
            Pelicula(
                4,
                "La Ciudad Perdida",
                2010,
                "Ana Martinez",
                "fantasia",
                false,
                2.0
            )
        )
        peliculas.add(
            Pelicula(
                5,
                "Operación Relámpago",
                2021,
                "Luis Hernandez",
                "accion",
                true,
                5.0
            )
        )
    }

    fun addPelicula(pelicula: Pelicula) = peliculas.add(pelicula)

    fun getPeliculas(): List<Pelicula> {
        return peliculas.toList()
    }

    fun deletePelicula(index: Int): Boolean {
        try {
            peliculas.removeAt(index)
            return true
        } catch (e: Error) {
            return false
        }
    }

    fun updatePelicula(pelicula: Pelicula, index: Int): Boolean {
        try {
            peliculas[index] = pelicula
            return true
        } catch (e: Error) {
            return false
        }
    }
}