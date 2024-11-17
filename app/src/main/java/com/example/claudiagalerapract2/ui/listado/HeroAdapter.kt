package com.example.claudiagalerapract2.ui.listado

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.claudiagalerapract2.R
import com.example.claudiagalerapract2.domain.modelo.Hero

class HeroAdapter(
    val actions: HeroesActions
) : ListAdapter<Hero, HeroItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view, parent, false)
        return HeroItemViewHolder(view, actions)
    }

    override fun onBindViewHolder(holder: HeroItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class DiffCallback : DiffUtil.ItemCallback<Hero>() {
        override fun areItemsTheSame(oldItem: Hero, newItem: Hero): Boolean {
            return oldItem.key == newItem.key
        }

        override fun areContentsTheSame(oldItem: Hero, newItem: Hero): Boolean {
            return oldItem == newItem
        }
    }

    interface HeroesActions {
        fun onItemClick(hero: Hero)

    }
}
