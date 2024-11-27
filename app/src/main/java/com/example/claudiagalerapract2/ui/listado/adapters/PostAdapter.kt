package com.example.claudiagalerapract2.ui.listado.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.claudiagalerapract2.R
import com.example.claudiagalerapract2.domain.modelo.Post
import com.example.claudiagalerapract2.ui.listado.viewholder.PostItemViewHolder

class PostAdapter (
    val actions: PostActions
) : ListAdapter<Post, PostItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_post, parent, false)
        return PostItemViewHolder(view, actions)
    }

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }

    interface PostActions {
        fun onItemClick(post: Post)

    }
}