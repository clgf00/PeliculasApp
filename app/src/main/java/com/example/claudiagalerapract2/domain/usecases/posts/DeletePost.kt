package com.example.claudiagalerapract2.domain.usecases.posts

import com.example.claudiagalerapract2.data.PostRepository
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import javax.inject.Inject

class DeletePost @Inject constructor(private val repository: PostRepository) {
    suspend operator fun invoke(id: Int): NetworkResult<Unit> {
        return repository.deletePost(id)
    }
}