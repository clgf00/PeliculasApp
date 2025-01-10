package com.example.claudiagalerapract2.ui.comment.listado

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.claudiagalerapract2.R
import com.example.claudiagalerapract2.databinding.FragmentListadoBinding
import com.example.claudiagalerapract2.domain.modelo.Comment
import com.example.claudiagalerapract2.ui.pantallamain.MainActivity
import com.example.claudiagalerapract2.ui.post.detalle.DetalleFragmentPostDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListadoCommentFragment : Fragment() {

    private val viewModel: ListadoCommentViewModel by viewModels()
    private var _binding: FragmentListadoBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CommentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListadoBinding.inflate(inflater, container, false)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_appbar_search, menu)
                val actionSearch = menu.findItem(R.id.search).actionView as SearchView

                actionSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean = false

                    override fun onQueryTextChange(newText: String?): Boolean {
                        viewModel.handleEvent(ListadoCommentEvent.FilterComments(newText.orEmpty()))
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.boton -> {
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.handleEvent(ListadoCommentEvent.GetComments)

        configureRecyclerView()
        observarState()
    }

    private fun observarState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    adapter.setComments(state.comments)
                    adapter.submitList(state.comments)
                    binding.lista.visibility = View.VISIBLE
                }
            }
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
