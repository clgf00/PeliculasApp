package com.example.claudiagalerapract2.ui.listado.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.claudiagalerapract2.databinding.CardViewBinding
import com.example.claudiagalerapract2.domain.modelo.Comment
import com.example.claudiagalerapract2.ui.listado.adapters.CommentAdapter

class CommentItemViewHolder (
    itemView: View,
    private val actions: CommentAdapter.CommentActions
) :
    RecyclerView.ViewHolder(itemView) {

    private val binding = CardViewBinding.bind(itemView)

    fun bind(comment: Comment) {
        with(binding) {
            title.text = comment.name
            subtitle.text = comment.email.let {
                it.lowercase().replaceFirstChar { char ->
                    if (char.isLowerCase()) char.titlecase() else char.toString()
                }
            }
            itemView.setOnClickListener {
                actions.onItemClick(comment)
            }

            itemView.setOnLongClickListener {
                true
            }
        }
    }
}