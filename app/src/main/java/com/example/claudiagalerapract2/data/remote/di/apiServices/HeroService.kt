package com.example.claudiagalerapract2.data.remote.di.apiServices

import com.example.claudiagalerapract2.data.remote.di.modelo.HeroRemote
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HeroService {

    @GET("/heroes")
    suspend fun getHeroes(@Query("hero") hero: String): Response<List<HeroRemote>>


    @GET("/heroes/{key}")
    suspend fun getHero(@Path("key") key: String): Response<HeroRemote>


    @DELETE("/heroes/{key}")
    suspend fun delHero(@Path("key") key: String): Response<Unit>

}


