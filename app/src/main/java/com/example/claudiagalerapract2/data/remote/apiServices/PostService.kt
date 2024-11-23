package com.example.claudiagalerapract2.data.remote.apiServices

import com.example.claudiagalerapract2.data.remote.di.modelo.PostRemote
import com.example.claudiagalerapract2.data.remote.di.modelo.UserRemote
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PostService {
    @GET("/posts")
    suspend fun get(): Response<List<PostRemote>>


    @GET("/posts/{id}")
    suspend fun get(@Path("id") key: Int): Response<PostRemote>


    @DELETE("/posts/{id}")
    suspend fun delete(@Path("id") key: Int): Response<Unit>


    @POST("/posts/{id}")
    suspend fun add(@Path("id") key: Int): Response<Unit>

    @PUT("/posts/{id}")
    suspend fun update(@Path("id") key: Int): Response<Unit>

}