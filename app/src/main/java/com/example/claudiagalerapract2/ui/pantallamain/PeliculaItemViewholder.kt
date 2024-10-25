package com.example.claudiagalerapract2.ui.pantallamain

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.claudiagalerapract2.databinding.CardViewBinding
import com.example.claudiagalerapract2.domain.modelo.Pelicula


class PeliculaItemViewholder(itemView: View, val actions: PeliculaAdapter.PeliculasActions) :
    RecyclerView.ViewHolder(itemView) {

    private val binding = CardViewBinding.bind(itemView)

    fun bind(item: Pelicula) {
        with(binding) {
            titulo.text = item.titulo
            director.text = item.director
            anyo.text = item.anyoEstreno.toString()
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