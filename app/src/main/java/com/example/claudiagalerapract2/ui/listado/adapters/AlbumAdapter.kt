package com.example.claudiagalerapract2.ui.listado.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.claudiagalerapract2.R
import com.example.claudiagalerapract2.domain.modelo.Album
import com.example.claudiagalerapract2.ui.listado.viewholder.AlbumItemViewHolder

class AlbumAdapter (
    val actions: AlbumActions
) : ListAdapter<Album, AlbumItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_post, parent, false)
        return AlbumItemViewHolder(view, actions)
    }

    override fun onBindViewHolder(holder: AlbumItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return currentList.size
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
