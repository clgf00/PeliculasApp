package com.example.claudiagalerapract2.ui.post.listado

sealed class ListadoPostEvent {
    data object GetPosts : ListadoPostEvent()
    data class FilterPosts(val query: String) : ListadoPostEvent()

}