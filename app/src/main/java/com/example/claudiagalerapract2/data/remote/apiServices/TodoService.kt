package com.example.claudiagalerapract2.data.remote.apiServices

import com.example.claudiagalerapract2.data.remote.di.modelo.TodoRemote
import com.example.claudiagalerapract2.data.remote.di.modelo.UserRemote
import com.example.claudiagalerapract2.domain.modelo.Todo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TodoService {

    @GET("/todos")
    suspend fun get(): Response<List<TodoRemote>>


    @GET("/todos/{id}")
    suspend fun get(@Path("id") key: Int): Response<TodoRemote>


    @DELETE("/todos/{id}")
    suspend fun delete(@Path("id") key: Int): Response<Unit>


    @POST("/todos/{id}")
    suspend fun add(@Body todo: Todo): Response<Todo>

    @PUT("/todos/{id}")
    suspend fun update(@Path("id") id: Int, @Body todo: String): Response<Todo>


}