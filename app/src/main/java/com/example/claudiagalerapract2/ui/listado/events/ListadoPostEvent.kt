package com.example.claudiagalerapract2.ui.listado.events


sealed class ListadoPostEvent {
    data object GetPosts : ListadoPostEvent()
    data class DeletePost(val postId: Int) : ListadoPostEvent()
    data class UpdatePost(val postId: Int, val updatedContent: String) : ListadoPostEvent()
    data class AddPost(val newPostContent: String) : ListadoPostEvent()

}