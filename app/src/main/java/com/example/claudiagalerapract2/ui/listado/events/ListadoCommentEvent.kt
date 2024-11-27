package com.example.claudiagalerapract2.ui.listado.events

sealed class ListadoCommentEvent {
    data object GetComments : ListadoCommentEvent()
    data class DeleteComment(val commentId: Int) : ListadoCommentEvent()
    data class UpdateComment(val commentId: Int, val updatedContent: String) : ListadoCommentEvent()
    data class AddComment(val newCommentContent: String) : ListadoCommentEvent()


}