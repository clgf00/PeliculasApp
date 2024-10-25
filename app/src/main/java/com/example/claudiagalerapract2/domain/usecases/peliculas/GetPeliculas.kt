package com.example.claudiagalerapract2.domain.usecases.peliculas

import com.example.claudiagalerapract2.data.RepositoryDos

class GetPeliculas {
    operator fun invoke() = RepositoryDos.getPeliculas()

}


