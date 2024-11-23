package com.example.claudiagalerapract2.data.remote.apiServices

import com.example.claudiagalerapract2.data.remote.di.modelo.PhotoRemote
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PhotoService {
    @GET("/photos")
    suspend fun get(): Response<List<PhotoRemote>>


    @GET("/photos/{id}")
    suspend fun get(@Path("id") key: Int): Response<PhotoRemote>


    @DELETE("/photos/{id}")
    suspend fun delete(@Path("id") key: Int): Response<Unit>


    @POST("/photos/{id}")
    suspend fun add(@Path("id") key: Int): Response<Unit>

    @PUT("/photos/{id}")
    suspend fun update(@Path("id") key: Int): Response<Unit>
}