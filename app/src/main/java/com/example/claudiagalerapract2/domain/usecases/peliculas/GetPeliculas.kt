package com.example.claudiagalerapract2.domain.usecases.peliculas

import com.example.claudiagalerapract2.data.Repository

class GetPeliculas (
    private val repo : Repository){
        operator fun invoke() = repo.getPeliculas()
    }

