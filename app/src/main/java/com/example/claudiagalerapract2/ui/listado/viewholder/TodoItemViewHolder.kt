package com.example.claudiagalerapract2.ui.listado.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.claudiagalerapract2.databinding.CardViewBinding
import com.example.claudiagalerapract2.databinding.FragmentDetalleTodoBinding
import com.example.claudiagalerapract2.domain.modelo.Todo
import com.example.claudiagalerapract2.ui.listado.adapters.TodoAdapter

class TodoItemViewHolder (
    itemView: View,
    private val actions: TodoAdapter.TodoActions
) :
    RecyclerView.ViewHolder(itemView) {

    private val binding = FragmentDetalleTodoBinding.bind(itemView)

    fun bind(todo: Todo) {
        with(binding) {
            todoTitle.text = todo.title
            todoCompleted.text = todo.completed.toString()
        }
            itemView.setOnClickListener {
                actions.onItemClick(todo)
            }

            itemView.setOnLongClickListener {
                true
            }
        }
    }
