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
import com.example.claudiagalerapract2.domain.modelo.Post
import com.example.claudiagalerapract2.ui.listado.adapters.PostAdapter
import com.example.claudiagalerapract2.ui.listado.events.ListadoPostEvent
import com.example.claudiagalerapract2.ui.listado.viewmodel.ListadoPostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListadoPostFragment : Fragment() {

    private val viewModel: ListadoPostViewModel by viewModels()
    private var _binding: FragmentListadoBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PostAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListadoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.handleEvent(ListadoPostEvent.GetPosts)

        configureRecyclerView()
        observarState()
    }

    private fun observarState() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.posts)
            binding.lista?.visibility = View.VISIBLE
        }
    }

    private fun configureRecyclerView() {
        adapter = PostAdapter(
            actions = object : PostAdapter.PostActions {
                override fun onItemClick(post: Post) {
                    val postId = post.id
                    val action = ListadoPostFragmentDirections.actionListadoPostFragmentToDetalleFragmentPost(postId)
                    findNavController().navigate(action)
                }
            })

        binding.lista?.layoutManager = LinearLayoutManager(activity)
        binding.lista?.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
