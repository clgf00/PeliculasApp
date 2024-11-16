package com.example.claudiagalerapract2.data

import com.example.claudiagalerapract2.data.remote.di.apiServices.HeroService
import com.example.claudiagalerapract2.data.remote.di.dataSource.HeroesRemoteDataSource
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.data.remote.di.modelo.toHero
import com.example.claudiagalerapract2.data.remote.di.modelo.toHeroDetail
import com.example.claudiagalerapract2.domain.modelo.Hero
import javax.inject.Inject


class HeroRepository@Inject constructor(
    private val heroService: HeroService,
    private val heroesRemoteDataSource: HeroesRemoteDataSource,

    )  {

    suspend fun fetchHeroes(): NetworkResult<List<Hero>?> {
        return heroesRemoteDataSource.fetchHeroes()

    }

    suspend fun fetchHero(key: String): NetworkResult<Hero> {

        try {
            val response = heroService.getHero(key)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body.toHeroDetail())
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }

    }

    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage")

}