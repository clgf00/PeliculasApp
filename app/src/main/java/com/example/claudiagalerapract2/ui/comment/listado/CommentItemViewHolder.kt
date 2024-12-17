package com.example.claudiagalerapract2.ui.comment.listado

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.claudiagalerapract2.databinding.CardviewCommentBinding
import com.example.claudiagalerapract2.domain.modelo.Comment

class CommentItemViewHolder(
    itemView: View,
    private val actions: CommentAdapter.CommentActions
) :
    RecyclerView.ViewHolder(itemView) {
    private val binding = CardviewCommentBinding.bind(itemView)

    fun bind(comment: Comment) {
        binding.commentTitle.text =comment.name
        itemView.setOnClickListener {
            actions.onItemClick(comment)
        }

        itemView.setOnLongClickListener {
            true
        }
    }
}