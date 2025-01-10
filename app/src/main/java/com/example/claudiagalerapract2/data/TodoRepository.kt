package com.example.claudiagalerapract2.data

import com.example.claudiagalerapract2.data.remote.apiServices.TodoService
import com.example.claudiagalerapract2.data.remote.di.dataSource.GalleryRemoteDataSource
import com.example.claudiagalerapract2.data.remote.di.di.IoDispatcher
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.data.remote.di.modelo.toTodoDetail
import com.example.claudiagalerapract2.domain.modelo.Todo
import com.example.claudiagalerapract2.ui.common.Constantes
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val todoService: TodoService,
    private val galleryRemoteDataSource: GalleryRemoteDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,


    ) {

    suspend fun fetchTodoConFlow(): Flow<NetworkResult<List<Todo>>> {
        return flow {

            emit(NetworkResult.Loading())
            val result = galleryRemoteDataSource.fetchTodos()
            emit(result)

        }
            .catch { e ->
                emit(NetworkResult.Error(e.message ?: Constantes.ERROR))
            }
            .flowOn(dispatcher)
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

    suspend fun updateTodo(id: Int, todo: String): NetworkResult<Todo> {
        return try {
            val response = todoService.update(id, todo)
            if (response.isSuccessful) {
                response.body()?.let {
                    return NetworkResult.Success(it)
                }
            }
            error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            error(e.message ?: e.toString())
        }
    }

    suspend fun deleteTodo(id: Int): NetworkResult<Unit> {
        return try {
            val response = todoService.delete(id)
            if (response.isSuccessful) {
                return NetworkResult.Success(Unit)
            }
            error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): NetworkResult<T> =
        NetworkResult.Error(message)

}
