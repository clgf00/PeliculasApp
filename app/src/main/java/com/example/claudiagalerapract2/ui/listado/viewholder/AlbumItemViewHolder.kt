package com.example.claudiagalerapract2.ui.listado.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.claudiagalerapract2.databinding.CardviewPostBinding
import com.example.claudiagalerapract2.domain.modelo.Album
import com.example.claudiagalerapract2.ui.listado.adapters.AlbumAdapter

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