package com.example.claudiagalerapract2.ui.album.listado

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.claudiagalerapract2.databinding.CardviewPostBinding
import com.example.claudiagalerapract2.domain.modelo.Album

class AlbumItemViewHolder (
    itemView: View,
    private val actions: AlbumAdapter.AlbumActions
) :
    RecyclerView.ViewHolder(itemView) {
    private val binding = CardviewPostBinding.bind(itemView)
    fun bind(album: Album) {
        with(binding) {
            title.text = album.title
            itemView.setOnClickListener {
                actions.onItemClick(album)
            }
        }
    }
}