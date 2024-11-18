package com.example.claudiagalerapract2.data.remote.di.dataSource

import com.example.claudiagalerapract2.data.remote.apiServices.HeroService
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.data.remote.di.modelo.toHero
import com.example.claudiagalerapract2.domain.modelo.Hero
import javax.inject.Inject

class HeroesRemoteDataSource @Inject constructor(
    private val heroService: HeroService
) : BaseApiResponse() {
    suspend fun fetchHeroes(): NetworkResult<List<Hero>?> =
        safeApiCall { heroService.getHeroes() }.map { lista ->
            lista?.map { it.toHero() }
        }
}

