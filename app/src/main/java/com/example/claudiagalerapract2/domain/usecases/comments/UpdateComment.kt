package com.example.claudiagalerapract2.domain.usecases.comments

import com.example.claudiagalerapract2.data.CommentRepository
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.modelo.Comment
import javax.inject.Inject

class UpdateComment @Inject constructor(private val repository: CommentRepository) {
    suspend operator fun invoke(id: Int, comment: Comment): NetworkResult<Comment> {
        return repository.updateComment(id, comment)
    }
}