package com.example.claudiagalerapract2.data

import com.example.claudiagalerapract2.data.remote.apiServices.CommentService
import com.example.claudiagalerapract2.data.remote.di.dataSource.GalleryRemoteDataSource
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.data.remote.di.modelo.toCommentDetail
import com.example.claudiagalerapract2.domain.modelo.Comment
import com.example.claudiagalerapract2.ui.common.Constantes
import javax.inject.Inject

class CommentRepository@Inject constructor(
    private val commentService: CommentService,
    private val galleryRemoteDataSource: GalleryRemoteDataSource,

    ) {

    suspend fun fetchComments(): NetworkResult<List<Comment>?> {
        return galleryRemoteDataSource.fetchComments()

    }
    suspend fun fetchComment(id: Int): NetworkResult<Comment> {

        try {
            val response = commentService.get(id)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body.toCommentDetail())
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }
    suspend fun addComment(comment: Comment): NetworkResult<Comment> {
        return try {
            val response = commentService.add(comment)
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

    suspend fun updateComment(id: Int, comment: Comment): NetworkResult<Comment> {
        return try {
            val response = commentService.update(id, comment)
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

    suspend fun deleteComment(id: Int): NetworkResult<Unit> {
        return try {
            val response = commentService.delete(id)
            if (response.isSuccessful) {
                return NetworkResult.Success(Unit)
            }
            error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            error(e.message ?: e.toString())
        }
    }

    suspend fun fetchCommentsByPost(postId: Int): List<Comment> {
        val response = commentService.getCommentsByPost(postId)
        if (response.isSuccessful) {
            return response.body()?.map { it.toCommentDetail() } ?: emptyList()
        } else {
            throw Exception(Constantes.ERROR_FETCH)
        }
    }
    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage")

}