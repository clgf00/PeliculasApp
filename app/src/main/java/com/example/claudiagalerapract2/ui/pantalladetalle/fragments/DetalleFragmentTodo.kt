package com.example.claudiagalerapract2.ui.pantalladetalle.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.claudiagalerapract2.databinding.FragmentDetalleTodoBinding
import com.example.claudiagalerapract2.domain.modelo.Todo
import com.example.claudiagalerapract2.ui.pantalladetalle.viewmodel.DetalleTodoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetalleFragmentTodo : Fragment() {

    private val viewModel: DetalleTodoViewModel by viewModels()
    private var _binding: FragmentDetalleTodoBinding? = null
    private val binding get() = _binding!!
    private val args: DetalleFragmentTodoArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleTodoBinding.inflate(inflater, container, false)
        val todoId = args.todoId

        observarViewModel()
        viewModel.cambiarTodo(todoId)
        return binding.root
    }

    private fun observarViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            state.mensaje?.let { error ->
                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
                viewModel.errorMostrado()
            }
            state.todo?.let { todo ->
                setTodo(todo)
            }
        }
    }

    private fun setTodo(todo: Todo) {
        binding.todoTitle.text = todo.title
        binding.todoCompleted.text = if (todo.completed) "Completed" else "Not Completed"
    }
}
