package com.example.claudiagalerapract2.ui.listado.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.claudiagalerapract2.databinding.FragmentDetallePostBinding
import com.example.claudiagalerapract2.domain.modelo.Post
import com.example.claudiagalerapract2.ui.listado.adapters.PostAdapter

class PostItemViewHolder (
    itemView: View,
    private val actions: PostAdapter.PostActions
) :
    RecyclerView.ViewHolder(itemView) {

    private val binding = FragmentDetallePostBinding.bind(itemView)

    fun bind(post: Post) {
        with(binding) {
            postTitle!!.text = post.title
            postBody!!.text = post.body

            itemView.setOnClickListener {
                actions.onItemClick(post)
            }
            itemView.setOnLongClickListener {
                true
            }
        }
    }
}