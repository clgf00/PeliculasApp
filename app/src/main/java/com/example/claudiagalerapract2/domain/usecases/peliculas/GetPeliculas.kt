package com.example.claudiagalerapract2.domain.usecases.peliculas

import com.example.claudiagalerapract2.data.Repository
import com.example.claudiagalerapract2.data.RepositoryDos
import com.example.claudiagalerapract2.domain.modelo.Pelicula

class GetPeliculas {
    operator fun invoke(): List<Pelicula> {
        return RepositoryDos.getPeliculas()
    }
}


