package com.example.claudiagalerapract2.data

import com.example.claudiagalerapract2.data.remote.apiServices.CommentService
import com.example.claudiagalerapract2.data.remote.di.dataSource.GalleryRemoteDataSource
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.data.remote.di.modelo.toCommentDetail
import com.example.claudiagalerapract2.domain.modelo.Comment
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
    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage")

}