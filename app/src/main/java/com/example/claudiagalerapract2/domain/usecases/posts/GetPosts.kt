package com.example.claudiagalerapract2.domain.usecases.posts

import com.example.claudiagalerapract2.data.PostRepository
import javax.inject.Inject

class GetPosts @Inject constructor(private val postRepository: PostRepository) {
    suspend operator fun invoke() = postRepository.fetchPostsConFlow()
}