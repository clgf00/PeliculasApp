package com.example.claudiagalerapract2.ui.post.listado

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.claudiagalerapract2.databinding.CardviewPostBinding
import com.example.claudiagalerapract2.domain.modelo.Post

class PostItemViewHolder (
    itemView: View,
    private val actions: PostAdapter.PostActions
) :
    RecyclerView.ViewHolder(itemView) {

    private val binding = CardviewPostBinding.bind(itemView)
    fun bind(post: Post) {
        with(binding) {
            title.text = post.title

            itemView.setOnClickListener {
                actions.onItemClick(post)
            }

            itemView.setOnLongClickListener {
                actions.onItemClick(post)
                true
            }
        }
    }
}