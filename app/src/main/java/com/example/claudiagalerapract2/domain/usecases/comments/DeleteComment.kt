package com.example.claudiagalerapract2.domain.usecases.comments

import com.example.claudiagalerapract2.data.CommentRepository
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import javax.inject.Inject

class DeleteComment @Inject constructor(private val repository: CommentRepository) {
    suspend operator fun invoke(id: Int): NetworkResult<Unit> {
        return repository.deleteComment(id)
    }
}