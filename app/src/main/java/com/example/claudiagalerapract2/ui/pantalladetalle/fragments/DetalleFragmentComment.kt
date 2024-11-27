package com.example.claudiagalerapract2.ui.pantalladetalle.fragments

import com.example.claudiagalerapract2.domain.modelo.Comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.claudiagalerapract2.databinding.FragmentDetalleCommentBinding
import com.example.claudiagalerapract2.ui.common.Constantes
import com.example.claudiagalerapract2.ui.pantalladetalle.viewmodel.DetalleCommentViewModel
import dagger.hilt.android.AndroidEntryPoint

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


                Toast.makeText(requireContext(), Constantes.ACTUALIZADO_EXITO, Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            } else {
                Toast.makeText(requireContext(), Constantes.ERROR, Toast.LENGTH_SHORT).show()
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
            state.comment?.let { comment ->
                setComment(comment)
            }
        }
    }
    private fun setComment(comment: Comment) {
        binding.commentBody.setText(comment.body)
        binding.commentName.setText(comment.name)
        binding.commentEmail.setText(comment.email)
    }
}
