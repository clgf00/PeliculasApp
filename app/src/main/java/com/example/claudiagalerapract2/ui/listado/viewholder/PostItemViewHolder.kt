package com.example.claudiagalerapract2.ui.listado.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.claudiagalerapract2.databinding.CardViewBinding
import com.example.claudiagalerapract2.domain.modelo.Post
import com.example.claudiagalerapract2.ui.listado.adapters.PostAdapter

class PostItemViewHolder (
    itemView: View,
    private val actions: PostAdapter.PostActions
) :
    RecyclerView.ViewHolder(itemView) {

    private val binding = CardViewBinding.bind(itemView)

    fun bind(post: Post) {
        with(binding) {
            title.text = post.title

            itemView.setOnClickListener {
                actions.onItemClick(post)
            }
            itemView.setOnLongClickListener {
                true
            }
        }
    }
}