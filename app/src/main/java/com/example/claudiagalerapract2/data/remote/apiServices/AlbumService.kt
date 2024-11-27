package com.example.claudiagalerapract2.data.remote.apiServices

import com.example.claudiagalerapract2.data.remote.di.modelo.AlbumRemote
import com.example.claudiagalerapract2.domain.modelo.Album
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AlbumService {

    @GET("/albums")
    suspend fun getAll(): Response<List<AlbumRemote>>


    @GET("/albums/{id}")
    suspend fun get(@Path("id") key: Int): Response<AlbumRemote>


    @DELETE("/albums/{id}")
    suspend fun delete(@Path("id") key: Int): Response<Unit>


    @POST("/albums/{id}")
    suspend fun add(@Body album: Album): Response<Album>

    @PUT("/albums/{id}")
    suspend fun update(@Path("id") id: Int, @Body album: Album): Response<Album>}
