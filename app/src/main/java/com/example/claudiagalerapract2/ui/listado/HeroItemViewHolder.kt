package com.example.claudiagalerapract2.ui.listado

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.claudiagalerapract2.databinding.CardViewBinding
import com.example.claudiagalerapract2.domain.modelo.Hero


class HeroItemViewHolder(itemView: View, val actions: HeroAdapter.HeroesActions) :
    RecyclerView.ViewHolder(itemView) {

    private val binding = CardViewBinding.bind(itemView)

    fun bind(item: Hero) {
        with(binding) {
            name.text = item.name
            itemView.setBackgroundResource(android.R.color.white)
            itemView.setOnLongClickListener {
                true
            }
            itemView.setOnClickListener {
                actions.onItemClick(item)
            }
        }
    }
}