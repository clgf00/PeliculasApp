package com.example.claudiagalerapract2.data

import com.example.claudiagalerapract2.data.remote.apiServices.PostService
import com.example.claudiagalerapract2.data.remote.di.dataSource.GalleryRemoteDataSource
import com.example.claudiagalerapract2.data.remote.di.di.IoDispatcher
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.data.remote.di.modelo.toPostDetail
import com.example.claudiagalerapract2.domain.modelo.Album
import com.example.claudiagalerapract2.domain.modelo.Post
import com.example.claudiagalerapract2.ui.common.Constantes
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val postService: PostService,
    private val galleryRemoteDataSource: GalleryRemoteDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,


    ) {

    suspend fun fetchPostsConFlow(): Flow<NetworkResult<List<Post>>> {
        return flow {

            emit(NetworkResult.Loading())
            val result = galleryRemoteDataSource.fetchPosts()
            emit(result)

        }
            .catch { e ->
                emit(NetworkResult.Error(e.message ?: Constantes.ERROR))
            }
            .flowOn(dispatcher)
    }


    suspend fun fetchPost(id: Int): NetworkResult<Post> {

        try {
            val response = postService.get(id)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body.toPostDetail())
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: CancellationException) {
            return error(e.message ?: e.toString())
        }
    }


    suspend fun updatePost(id: Int, post: Post): NetworkResult<Post> {
        return try {
            val response = postService.update(id, post)
            if (response.isSuccessful) {
                response.body()?.let {
                    return NetworkResult.Success(it)
                }
            }
            error("${response.code()} ${response.message()}")
        } catch (e: CancellationException) {
            error(e.message ?: e.toString())
        }
    }

    suspend fun deletePost(id: Int): NetworkResult<Unit> {
        return try {
            val response = postService.delete(id)

            if (response.isSuccessful) {
                return NetworkResult.Success(Unit)
            } else {
                return error("${response.code()} ${response.message()}")
            }
        } catch (e: CancellationException) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): NetworkResult<T> =
        NetworkResult.Error(message)

}