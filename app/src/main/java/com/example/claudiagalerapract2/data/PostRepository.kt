package com.example.claudiagalerapract2.data

import com.example.claudiagalerapract2.data.remote.apiServices.PostService
import com.example.claudiagalerapract2.data.remote.di.dataSource.GalleryRemoteDataSource
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.data.remote.di.modelo.toPostDetail
import com.example.claudiagalerapract2.domain.modelo.Post
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val postService: PostService,
    private val galleryRemoteDataSource: GalleryRemoteDataSource,

    ) {

    suspend fun fetchPosts(): NetworkResult<List<Post>?> {
        return galleryRemoteDataSource.fetchPosts()

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
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    suspend fun addPost(post: Post): NetworkResult<Post> {
        return try {
            val response = postService.add(post)
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

    suspend fun updatePost(id: Int, post: Post): NetworkResult<Post> {
        return try {
            val response = postService.update(id, post)
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

    suspend fun deletePost(id: Int): NetworkResult<Unit> {
        return try {
            val response = postService.delete(id)

            if (response.isSuccessful) {
                return NetworkResult.Success(Unit)
            } else {
                return error("${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage")

}