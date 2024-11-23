package com.example.claudiagalerapract2.ui.listado.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.claudiagalerapract2.R
import com.example.claudiagalerapract2.domain.modelo.Photo
import com.example.claudiagalerapract2.ui.listado.viewholder.PhotoItemViewHolder

class PhotoAdapter (
    val actions: PhotoActions
) : ListAdapter<Photo, PhotoItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_detalle_photo, parent, false)
        return PhotoItemViewHolder(view, actions)
    }

    override fun onBindViewHolder(holder: PhotoItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class DiffCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
    }

    interface PhotoActions {
        fun onItemClick(photo: Photo)

    }
}