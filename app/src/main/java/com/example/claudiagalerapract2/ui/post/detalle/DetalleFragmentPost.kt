package com.example.claudiagalerapract2.ui.post.detalle


import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.claudiagalerapract2.R
import com.example.claudiagalerapract2.databinding.FragmentDetallePostBinding
import com.example.claudiagalerapract2.domain.modelo.Comment
import com.example.claudiagalerapract2.domain.modelo.Post
import com.example.claudiagalerapract2.ui.comment.detalle.DetalleCommentEvent
import com.example.claudiagalerapract2.ui.comment.listado.CommentAdapter
import com.example.claudiagalerapract2.ui.comment.listado.ListadoCommentEvent
import com.example.claudiagalerapract2.ui.pantallamain.MainActivity
import com.example.claudiagalerapract2.ui.post.listado.DetallePostEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetalleFragmentPost : Fragment() {

    private val viewModel: DetallePostViewModel by viewModels()
    private var _binding: FragmentDetallePostBinding? = null
    private val binding get() = _binding!!
    private val args: DetalleFragmentPostArgs by navArgs()
    private lateinit var commentAdapter: CommentAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetallePostBinding.inflate(inflater, container, false)
        configureRecyclerView()
        viewModel.cambiarPost(args.postId)
        observarViewModel()

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_appbar_search, menu)

                val actionSearch = menu.findItem(R.id.search).actionView as SearchView
                actionSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean = false

                    override fun onQueryTextChange(newText: String?): Boolean {
                        viewModel.handleEvent(DetallePostEvent.FilterComments(newText.orEmpty()))
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

        binding.deleteAlbumButton.setOnClickListener {
            viewModel.eliminarPost(args.postId)
            findNavController().navigateUp()
        }

        return binding.root
    }


    private fun configureRecyclerView() {
        commentAdapter = CommentAdapter(object : CommentAdapter.CommentActions {
            override fun onItemClick(comment: Comment) {
                val action = DetalleFragmentPostDirections
                    .actionDetalleFragmentPostToDetalleFragmentComment(comment.id)
                findNavController().navigate(action)
            }
        })

        binding.commentsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.commentsRecyclerView.adapter = commentAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observarViewModel()

    }

    private fun observarViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    state.mensaje?.let { error ->
                        Snackbar.make(requireView(), error, Snackbar.LENGTH_SHORT).show()
                        viewModel.errorMostrado()
                    }

                    state.post?.let { post ->
                        setPost(post)
                    }

                    state.comments.let { comments ->
                        commentAdapter.submitList(comments)
                    }
                }
            }
        }
    }

    private fun setPost(post: Post) {
        binding.postTitle.setText(post.title)
        binding.postBody.setText(post.body)
    }
}
