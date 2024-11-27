package com.example.claudiagalerapract2.ui.listado.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.claudiagalerapract2.R
import com.example.claudiagalerapract2.domain.modelo.Comment
import com.example.claudiagalerapract2.ui.listado.viewholder.CommentItemViewHolder

class CommentAdapter(
    val actions: CommentActions
) : ListAdapter<Comment, CommentItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_comment, parent, false)
        return CommentItemViewHolder(view, actions)
    }

    override fun onBindViewHolder(holder: CommentItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class DiffCallback : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem == newItem
        }
    }

    interface CommentActions {
        fun onItemClick(comment: Comment)

    }
}