package com.example.claudiagalerapract2.ui.pantallamain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.claudiagalerapract2.R
import com.example.claudiagalerapract2.domain.modelo.Pelicula


class PeliculaAdapter(
    val itemClick: (Pelicula) -> Unit,
    val actions: PeliculasActions
) : ListAdapter<Pelicula, PeliculaItemViewholder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculaItemViewholder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view, parent, false)
        return PeliculaItemViewholder(view, actions)
    }

    override fun onBindViewHolder(holder: PeliculaItemViewholder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class DiffCallback : DiffUtil.ItemCallback<Pelicula>() {
        override fun areItemsTheSame(oldItem: Pelicula, newItem: Pelicula): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Pelicula, newItem: Pelicula): Boolean {
            return oldItem == newItem
        }
    }

    interface PeliculasActions {
        fun onItemClick(pelicula: Pelicula)

    }
}
