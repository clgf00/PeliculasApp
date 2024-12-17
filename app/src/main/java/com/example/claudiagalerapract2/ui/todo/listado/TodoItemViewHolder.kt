package com.example.claudiagalerapract2.ui.todo.listado

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.claudiagalerapract2.databinding.CardviewTodoBinding
import com.example.claudiagalerapract2.domain.modelo.Todo

class TodoItemViewHolder (
    itemView: View,
    private val actions: TodoAdapter.TodoActions
) :
    RecyclerView.ViewHolder(itemView) {

    private val binding = CardviewTodoBinding.bind(itemView)

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
