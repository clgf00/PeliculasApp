package com.example.claudiagalerapract2.domain.usecases.peliculas

import com.example.claudiagalerapract2.data.RepositoryDos
import com.example.claudiagalerapract2.domain.modelo.Pelicula

class AddPeliculaUseCase {

    operator fun invoke(pelicula: Pelicula): Boolean{
        return RepositoryDos.addPelicula(pelicula)
    }
}