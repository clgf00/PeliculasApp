package com.example.claudiagalerapract2.ui.listado.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.claudiagalerapract2.databinding.CardViewBinding
import com.example.claudiagalerapract2.domain.modelo.User
import com.example.claudiagalerapract2.ui.listado.adapters.UserAdapter


class UserItemViewHolder(
    itemView: View,
    private val actions: UserAdapter.UserActions
) :
    RecyclerView.ViewHolder(itemView) {

    private val binding = CardViewBinding.bind(itemView)

    fun bind(user: User) {
        with(binding) {
            title.text = user.name
            subtitle.text = user.username.let {
                it.lowercase().replaceFirstChar { char ->
                    if (char.isLowerCase()) char.titlecase() else char.toString()
                }
            }
            itemView.setOnClickListener {
                actions.onItemClick(user)
            }

            itemView.setOnLongClickListener {
                true
            }
        }
    }
}