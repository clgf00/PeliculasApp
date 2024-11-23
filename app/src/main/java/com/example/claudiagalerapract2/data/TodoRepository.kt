package com.example.claudiagalerapract2.data

import com.example.claudiagalerapract2.data.remote.apiServices.TodoService
import com.example.claudiagalerapract2.data.remote.di.dataSource.GalleryRemoteDataSource
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.data.remote.di.modelo.toTodoDetail
import com.example.claudiagalerapract2.domain.modelo.Todo
import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val todoService: TodoService,
    private val galleryRemoteDataSource: GalleryRemoteDataSource,

    ) {

    suspend fun fetchTodos(): NetworkResult<List<Todo>?> {
        return galleryRemoteDataSource.fetchTodos()

    }

    suspend fun fetchTodo(id: Int): NetworkResult<Todo> {

        try {
            val response = todoService.get(id)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body.toTodoDetail())
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
