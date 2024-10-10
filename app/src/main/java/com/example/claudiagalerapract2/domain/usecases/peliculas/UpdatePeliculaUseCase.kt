package com.example.claudiagalerapract2.domain.usecases.peliculas

import com.example.claudiagalerapract2.data.RepositoryDos
import com.example.claudiagalerapract2.domain.modelo.Pelicula

class UpdatePeliculaUseCase {
    operator fun invoke(pelicula: Pelicula, index: Int,): Boolean {
        return RepositoryDos.updatePelicula(pelicula, index)
    }
}