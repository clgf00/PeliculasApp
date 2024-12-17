package com.example.claudiagalerapract2.ui.photo.listado

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.claudiagalerapract2.databinding.CardviewPhotoBinding
import com.example.claudiagalerapract2.domain.modelo.Photo

class PhotoItemViewHolder(
    itemView: View,
    private val actions: PhotoAdapter.PhotoActions
) :
    RecyclerView.ViewHolder(itemView) {

    private val binding = CardviewPhotoBinding.bind(itemView)

    fun bind(photo: Photo) {
        with(binding) {
            photoTitleEditText.text = photo.title
            photoImageView.load(photo.thumbnailUrl)
            itemView.setBackgroundColor(
                if (itemView.isSelected) Color.LTGRAY else Color.WHITE
            )
            itemView.setOnClickListener {
                actions.onItemClick(photo)
            }
        }
    }
}
