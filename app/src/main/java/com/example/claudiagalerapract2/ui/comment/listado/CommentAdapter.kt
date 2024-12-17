package com.example.claudiagalerapract2.ui.comment.listado

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.claudiagalerapract2.R
import com.example.claudiagalerapract2.domain.modelo.Comment

class CommentAdapter(
    val actions: CommentActions
) : ListAdapter<Comment, CommentItemViewHolder>(DiffCallback()) {
    private var allComments: List<Comment> = listOf()
    private var displayedComments: List<Comment> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_comment, parent, false)
        return CommentItemViewHolder(view, actions)
    }

    override fun onBindViewHolder(holder: CommentItemViewHolder, position: Int) {
        val comment = currentList[position]
        holder.bind(comment)


    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    fun setComments(comments: List<Comment>) {
        allComments = comments
        displayedComments = comments
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