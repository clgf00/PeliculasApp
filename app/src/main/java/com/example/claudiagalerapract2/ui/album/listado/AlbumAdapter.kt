package com.example.claudiagalerapract2.ui.album.listado

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.claudiagalerapract2.R
import com.example.claudiagalerapract2.domain.modelo.Album

class AlbumAdapter (
    val actions: AlbumActions
) : ListAdapter<Album, AlbumItemViewHolder>(DiffCallback()) {
    private var allAlbums: List<Album> = listOf()
    private var displayedAlbums: List<Album> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_post, parent, false)
        return AlbumItemViewHolder(view, actions)
    }

    override fun onBindViewHolder(holder: AlbumItemViewHolder, position: Int) {
        holder.bind(displayedAlbums[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
   }

    fun setAlbums(albums: List<Album>) {
        allAlbums = albums
        displayedAlbums = albums
    }

    class DiffCallback : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem == newItem
        }
    }

    interface AlbumActions {
        fun onItemClick(album: Album)
    }
}
