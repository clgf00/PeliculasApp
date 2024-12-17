package com.example.claudiagalerapract2.ui.comment.detalle

sealed class DetalleCommentEvent {
    data object GetComments: DetalleCommentEvent()
    data class DeleteComment(val commentId: Int) : DetalleCommentEvent()
    data class UpdateComment(val commentId: Int, val updatedContent: String) : DetalleCommentEvent()
    data class AddComment(val newCommentContent: String) : DetalleCommentEvent()
    data class FilterComments(val query: String) : DetalleCommentEvent()

}