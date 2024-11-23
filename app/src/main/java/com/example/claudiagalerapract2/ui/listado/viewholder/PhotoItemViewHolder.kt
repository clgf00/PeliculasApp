package com.example.claudiagalerapract2.ui.listado.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.claudiagalerapract2.databinding.CardViewBinding
import com.example.claudiagalerapract2.domain.modelo.Photo
import com.example.claudiagalerapract2.ui.listado.adapters.PhotoAdapter

class PhotoItemViewHolder (
    itemView: View,
    private val actions: PhotoAdapter.PhotoActions
) :
    RecyclerView.ViewHolder(itemView) {

    private val binding = CardViewBinding.bind(itemView)

    fun bind(photo: Photo) {
        with(binding) {
            title.text = photo.title
            image.load(photo.thumbnailUrl)
            itemView.setOnClickListener {
                actions.onItemClick(photo)
            }

            itemView.setOnLongClickListener {
                true
            }
        }
    }
}