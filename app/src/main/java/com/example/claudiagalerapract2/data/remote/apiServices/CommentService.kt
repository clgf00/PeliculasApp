package com.example.claudiagalerapract2.data.remote.apiServices

import com.example.claudiagalerapract2.data.remote.di.modelo.CommentRemote
import com.example.claudiagalerapract2.data.remote.di.modelo.UserRemote
import org.w3c.dom.Comment
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CommentService {
    @GET("/comments")
    suspend fun get(): Response<List<CommentRemote>>


    @GET("/comments/{id}")
    suspend fun get(@Path("id") key: Int): Response<CommentRemote>


    @DELETE("/comments/{id}")
    suspend fun delete(@Path("id") key: Int): Response<Unit>


    @POST("/comments/{id}")
    suspend fun add(@Path("id") key: Int): Response<Unit>

    @PUT("/comments/{id}")
    suspend fun update(@Path("id") key: Int): Response<Unit>

}