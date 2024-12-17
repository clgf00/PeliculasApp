package com.example.claudiagalerapract2.ui.todo.detalle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.claudiagalerapract2.databinding.FragmentDetalleTodoBinding
import com.example.claudiagalerapract2.domain.modelo.Todo
import com.example.claudiagalerapract2.ui.common.Constantes
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

        binding.deleteTodoButton.setOnClickListener {
            viewModel.eliminarTodo(todoId)
            Snackbar.make(requireView(), Constantes.ELIMINADO, Snackbar.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
        binding.updateTodoButton.setOnClickListener {
            val updatedBody = binding.todoTitle.text.toString()
            if (updatedBody.isNotEmpty()) {
                viewModel.actualizarTodo(todoId, updatedBody)
                Snackbar.make(requireView(), Constantes.ACTUALIZADO_EXITO, Snackbar.LENGTH_SHORT).show()
                findNavController().navigateUp()
            } else {
                Snackbar.make(requireView(), Constantes.ERROR, Snackbar.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    private fun observarViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    state.mensaje?.let { error ->
                        Snackbar.make(requireView(), error, Snackbar.LENGTH_SHORT).show()
                        viewModel.errorMostrado()
                    }

                    state.todo?.let { todo ->
                        setTodo(todo)
                    }
                }
            }
        }
    }

    private fun setTodo(todo: Todo) {
        binding.todoTitle.setText(todo.title)
        binding.todoCompleted.text = if (todo.completed) Constantes.COMPLETED else Constantes.NOT_COMPLETED
    }
}
