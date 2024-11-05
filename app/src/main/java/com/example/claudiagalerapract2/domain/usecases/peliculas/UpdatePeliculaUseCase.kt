package com.example.claudiagalerapract2.domain.usecases.peliculas

import com.example.claudiagalerapract2.data.Repository
import com.example.claudiagalerapract2.domain.modelo.Pelicula
import javax.inject.Inject

class UpdatePeliculaUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(pelicula: Pelicula, index: Int): Boolean {
        return repository.updatePelicula(pelicula, index)
    }
}