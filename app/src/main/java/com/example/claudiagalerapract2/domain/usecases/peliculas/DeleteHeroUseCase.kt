package com.example.claudiagalerapract2.domain.usecases.peliculas

import com.example.claudiagalerapract2.data.remote.di.dataSource.HeroesRemoteDataSource
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import javax.inject.Inject


class DeleteHeroUseCase @Inject constructor(
    private val heroesRemoteDataSource: HeroesRemoteDataSource
) {
    suspend operator fun invoke(key: String): NetworkResult<Boolean> {
        return heroesRemoteDataSource.delHero(key)
    }
}
