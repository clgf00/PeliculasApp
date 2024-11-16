package com.example.claudiagalerapract2.domain.usecases.peliculas

import com.example.claudiagalerapract2.data.HeroRepository
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.modelo.Hero
import javax.inject.Inject

class GetHero @Inject constructor(private val repository: HeroRepository) {
    suspend operator fun invoke(key: String): NetworkResult<Hero> {
        return repository.fetchHero(key)
    }
}

