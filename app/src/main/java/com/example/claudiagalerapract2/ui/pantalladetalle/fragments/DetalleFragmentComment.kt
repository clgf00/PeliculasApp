package com.example.claudiagalerapract2.ui.pantalladetalle.fragments

import com.example.claudiagalerapract2.domain.modelo.Comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.claudiagalerapract2.databinding.FragmentDetalleCommentBinding
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
        binding.commentBody.text = comment.body
    }
}
