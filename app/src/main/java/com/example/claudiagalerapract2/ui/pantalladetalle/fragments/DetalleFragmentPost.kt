package com.example.claudiagalerapract2.ui.pantalladetalle.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.claudiagalerapract2.databinding.FragmentDetallePostBinding
import com.example.claudiagalerapract2.domain.modelo.Comment
import com.example.claudiagalerapract2.domain.modelo.Post
import com.example.claudiagalerapract2.ui.common.Constantes
import com.example.claudiagalerapract2.ui.listado.adapters.CommentAdapter
import com.example.claudiagalerapract2.ui.pantalladetalle.viewmodel.DetallePostViewModel
import dagger.hilt.android.AndroidEntryPoint

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
        val postId = args.postId
        commentAdapter = CommentAdapter(object : CommentAdapter.CommentActions {
            override fun onItemClick(comment: Comment) {
                val action = DetalleFragmentPostDirections.actionDetalleFragmentPostToDetalleFragmentComment(comment.id)
                findNavController().navigate(action)
            }
        })
        observarViewModel()

        viewModel.cambiarPost(postId)
        binding.commentsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.commentsRecyclerView.adapter = commentAdapter
        binding.deleteAlbumButton.setOnClickListener {
            viewModel.eliminarPost(postId)
            Toast.makeText(requireContext(), Constantes.ELIMINADO, Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
        binding.updatePostButton.setOnClickListener {
            val updatedBody = binding.postBody.text.toString()
            if (updatedBody.isNotEmpty()) {
                viewModel.actualizarPost(postId, updatedBody)
                Toast.makeText(requireContext(), Constantes.ACTUALIZADO_EXITO, Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigateUp()
            } else {
                Toast.makeText(
                    requireContext(),
                    Constantes.ERROR,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return binding.root
    }

    private fun observarViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            state.mensaje?.let { error ->
                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
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

    private fun setPost(post: Post) {
        binding.postTitle.setText(post.title)
        binding.postBody.setText(post.body)
    }
}
