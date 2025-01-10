package com.example.claudiagalerapract2.data.remote.apiServices

import com.example.claudiagalerapract2.data.remote.di.modelo.UserRemote
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {

    @GET("/users")
    suspend fun get(): Response<List<UserRemote>>


    @GET("/users/{id}")
    suspend fun get(@Path("id") key: Int): Response<UserRemote>


}


