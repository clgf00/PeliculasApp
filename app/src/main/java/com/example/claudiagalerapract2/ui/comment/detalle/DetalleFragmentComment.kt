package com.example.claudiagalerapract2.ui.comment.detalle

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
import com.example.claudiagalerapract2.databinding.FragmentDetalleCommentBinding
import com.example.claudiagalerapract2.domain.modelo.Comment
import com.example.claudiagalerapract2.ui.common.Constantes
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetalleFragmentComment : Fragment() {

    private val viewModel: DetalleCommentViewModel by viewModels()
    private var _binding: FragmentDetalleCommentBinding? = null
    private val binding get() = _binding!!
    private val args: DetalleFragmentCommentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleCommentBinding.inflate(inflater, container, false)
        val commentId = args.commentId

        observarViewModel()
        viewModel.cambiarComment(commentId)
        binding.updateCommentButton.setOnClickListener {
            val updatedBody = binding.commentBody.text.toString()
            if (updatedBody.isNotEmpty()) {
                val updatedComment = Comment(
                    id = args.commentId,
                    postId = 1,
                    name = binding.commentName.text.toString(),
                    email = binding.commentEmail.text.toString(),
                    body = updatedBody
                )

                viewModel.actualizarComment(updatedComment.id, updatedComment)


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
                    state.comment?.let { comment ->
                        setComment(comment)
                    }
                }
            }
        }
    }

    private fun setComment(comment: Comment) {
        binding.commentBody.setText(comment.body)
        binding.commentName.setText(comment.name)
        binding.commentEmail.setText(comment.email)
    }
}
