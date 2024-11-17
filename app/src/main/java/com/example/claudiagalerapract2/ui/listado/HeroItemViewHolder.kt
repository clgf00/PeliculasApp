package com.example.claudiagalerapract2.ui.listado

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.claudiagalerapract2.databinding.CardViewBinding
import com.example.claudiagalerapract2.domain.modelo.Hero


class HeroItemViewHolder(
    itemView: View,
    private val actions: HeroAdapter.HeroesActions
) :
    RecyclerView.ViewHolder(itemView) {

    private val binding = CardViewBinding.bind(itemView)

    fun bind(hero: Hero) {
        with(binding) {
            name.text = hero.name
            role.text = hero.role.let {
                it.lowercase().replaceFirstChar { char ->
                    if (char.isLowerCase()) char.titlecase() else char.toString()
                }
            }

            hero.portrait.let { imageUrl ->
                heroImage.load(imageUrl) {
                    crossfade(true)
                }
            }
            itemView.setOnClickListener {
                actions.onItemClick(hero)
            }

            itemView.setOnLongClickListener {
                true
            }
        }
    }
}