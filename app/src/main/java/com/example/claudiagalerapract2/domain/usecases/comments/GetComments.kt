package com.example.claudiagalerapract2.domain.usecases.comments

import com.example.claudiagalerapract2.data.CommentRepository
import javax.inject.Inject

class GetComments @Inject constructor(private val commentRepository: CommentRepository) {
    suspend operator fun invoke() = commentRepository.fetchComments()
}