package com.example.claudiagalerapract2.domain.usecases.peliculas

import com.example.claudiagalerapract2.data.RepositoryDos
import com.example.claudiagalerapract2.domain.modelo.Pelicula

class DeletePeliculaUseCase {
    operator fun invoke(index: Int): Boolean{
        return RepositoryDos.deletePelicula(index)
    }
}