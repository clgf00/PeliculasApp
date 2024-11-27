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
import com.example.claudiagalerapract2.domain.modelo.Comment
import com.example.claudiagalerapract2.ui.listado.adapters.CommentAdapter
import com.example.claudiagalerapract2.ui.listado.events.ListadoCommentEvent
import com.example.claudiagalerapract2.ui.listado.viewmodel.ListadoCommentViewModel
import com.example.claudiagalerapract2.ui.pantalladetalle.fragments.DetalleFragmentPostDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListadoCommentFragment : Fragment() {

    private val viewModel: ListadoCommentViewModel by viewModels()
    private var _binding: FragmentListadoBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CommentAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListadoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.handleEvent(ListadoCommentEvent.GetComments)

        configureRecyclerView()
        observarState()
    }

    private fun observarState() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.comments)
            binding.lista.visibility = View.VISIBLE
        }
    }

    private fun configureRecyclerView() {
        adapter = CommentAdapter(
            actions = object : CommentAdapter.CommentActions {
                override fun onItemClick(comment: Comment) {
                    val commentId = comment.id
                    val action = DetalleFragmentPostDirections.actionDetalleFragmentPostToDetalleFragmentComment(comment.id)
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
