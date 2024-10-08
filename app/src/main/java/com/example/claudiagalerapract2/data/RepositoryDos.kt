package com.example.claudiagalerapract2.data

import com.example.claudiagalerapract2.domain.modelo.Pelicula

object RepositoryDos {

    private val peliculas = mutableListOf<Pelicula>()

    init {
        peliculas.add(Pelicula("", null, " ", "", false, 0))
        peliculas.add(Pelicula("El Gran Escape", 1998, "Juan Perez", "Accion", false, 4))
        peliculas.add(Pelicula("Viaje al Futuro", 2002, "Maria Lopez", "terror", true, 8))
        peliculas.add(Pelicula("La Batalla Final", 2013, "Jose Rodriguez", "Aventuras", true, 7))

    }

    private val mapPeliculas = mutableMapOf<String, Pelicula>()

    fun addPelicula(pelicula: Pelicula) =
        peliculas.add(pelicula)

    fun getPeliculas(): List<Pelicula>{
        return peliculas
    }


}