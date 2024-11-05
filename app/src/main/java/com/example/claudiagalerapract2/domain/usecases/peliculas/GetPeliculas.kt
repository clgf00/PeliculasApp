package com.example.claudiagalerapract2.domain.usecases.peliculas

import com.example.claudiagalerapract2.data.Repository
import javax.inject.Inject

class GetPeliculas @Inject constructor(private val repository: Repository) {
    operator fun invoke() = repository.getPeliculas()
}

