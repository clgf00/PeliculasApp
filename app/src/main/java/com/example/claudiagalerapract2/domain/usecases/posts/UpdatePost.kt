package com.example.claudiagalerapract2.domain.usecases.posts

import com.example.claudiagalerapract2.data.PostRepository
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.modelo.Post
import javax.inject.Inject

class UpdatePost @Inject constructor(private val repository: PostRepository) {
    suspend operator fun invoke(id: Int, post: Post): NetworkResult<Post> {
        return repository.updatePost(id, post)
    }
}