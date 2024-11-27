package com.example.claudiagalerapract2.domain.usecases.comments

import com.example.claudiagalerapract2.data.CommentRepository
import com.example.claudiagalerapract2.domain.modelo.Comment
import javax.inject.Inject

class GetPostComments @Inject constructor(
    private val commentRepository: CommentRepository
) {
    suspend operator fun invoke(postId: Int): List<Comment> {
        return commentRepository.fetchCommentsByPost(postId)
    }
}