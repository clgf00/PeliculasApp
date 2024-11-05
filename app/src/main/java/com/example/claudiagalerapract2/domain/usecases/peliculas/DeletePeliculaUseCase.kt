package com.example.claudiagalerapract2.domain.usecases.peliculas

import com.example.claudiagalerapract2.data.Repository
import javax.inject.Inject

class DeletePeliculaUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(index: Int): Boolean {
        return repository.deletePelicula(index)
    }
}