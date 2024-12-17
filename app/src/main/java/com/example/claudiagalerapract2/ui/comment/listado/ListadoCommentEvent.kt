package com.example.claudiagalerapract2.ui.comment.listado

sealed class ListadoCommentEvent {
    data object GetComments : ListadoCommentEvent()
    data class FilterComments(val query: String) : ListadoCommentEvent()

}