package com.example.claudiagalerapract2.domain.usecases.comments

import com.example.claudiagalerapract2.data.CommentRepository
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.modelo.Comment
import javax.inject.Inject

class GetComment @Inject constructor(private val repository: CommentRepository) {
    suspend operator fun invoke(key: Int): NetworkResult<Comment> {
        return repository.fetchComment(key)
    }
}