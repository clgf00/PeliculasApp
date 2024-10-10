package com.example.claudiagalerapract2.data

import com.example.claudiagalerapract2.domain.modelo.Pelicula

class Repository {

    fun addPelicula(pelicula: Pelicula) = peliculas.add(pelicula)

    fun getPeliculas(): List<Pelicula> {
        return peliculas
    }

    init {
        // peliculas.add(Pelicula("", null, " ", "", false, 0))
        peliculas.add(Pelicula("El Gran Escape", 1998, "Juan Perez", "accion", false, 1))
        peliculas.add(Pelicula("Viaje al Futuro", 2002, "Maria Lopez", "terror", true, 3))
        peliculas.add(Pelicula("La Batalla Final", 2013, "Jose Rodriguez", "comedia", true, 5))
        peliculas.add(Pelicula("Misión Rescate", 2015, "Carlos García", "aventura", true, 4))
        peliculas.add(Pelicula("La Ciudad Perdida", 2010, "Ana Martinez", "drama", false, 2))
        peliculas.add(Pelicula("Operación Relámpago", 2021, "Luis Hernandez", "acción", true, 5))
    }

    companion object {
        //companion object significa que es estatico.  Esto permite que la lista sea compartida entre todas las instancias de la clase Repository.
        // Es como tener una única lista de películas para toda la aplicación.
        private val peliculas = mutableListOf<Pelicula>()
        fun getInstance(): Repository = Repository()
    }
}