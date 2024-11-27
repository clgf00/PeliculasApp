package com.example.claudiagalerapract2.ui.listado.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.claudiagalerapract2.databinding.FragmentListadoBinding
import com.example.claudiagalerapract2.domain.modelo.Todo
import com.example.claudiagalerapract2.ui.listado.adapters.TodoAdapter
import com.example.claudiagalerapract2.ui.listado.events.ListadoTodoEvent
import com.example.claudiagalerapract2.ui.listado.viewmodel.ListadoTodoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListadoTodoFragment : Fragment() {

    private val viewModel: ListadoTodoViewModel by viewModels()
    private var _binding: FragmentListadoBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: TodoAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListadoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.handleEvent(ListadoTodoEvent.GetTodos)

        configureRecyclerView()
        observarState()
    }

    private fun observarState() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.todos)
        }
    }

    private fun configureRecyclerView() {
        adapter = TodoAdapter(
            actions = object : TodoAdapter.TodoActions {
                override fun onItemClick(todo: Todo) {
                    val todoId = todo.id
                    val action = ListadoTodoFragmentDirections.actionListadoTodoFragmentToDetalleFragmentTodo(todoId)
                    findNavController().navigate(action)
                }
            })

        binding.lista.layoutManager = LinearLayoutManager(activity)
        binding.lista.adapter = adapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
