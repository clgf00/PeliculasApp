package com.example.claudiagalerapract2.ui.post.listado


sealed class DetallePostEvent {
    data object GetComments : DetallePostEvent()
    data class DeletePost(val postId: Int) : DetallePostEvent()
    data class UpdatePost(val postId: Int, val updatedContent: String) : DetallePostEvent()
    data class FilterComments(val query: String) : DetallePostEvent()


}