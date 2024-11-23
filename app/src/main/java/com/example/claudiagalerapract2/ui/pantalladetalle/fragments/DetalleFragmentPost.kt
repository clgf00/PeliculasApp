package com.example.claudiagalerapract2.ui.pantalladetalle.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.claudiagalerapract2.databinding.FragmentDetallePostBinding
import com.example.claudiagalerapract2.domain.modelo.Post
import com.example.claudiagalerapract2.ui.pantalladetalle.viewmodel.DetallePostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetalleFragmentPost : Fragment() {

    private val viewModel: DetallePostViewModel by viewModels()
    private var _binding: FragmentDetallePostBinding? = null
    private val binding get() = _binding!!
    private val args: DetalleFragmentPostArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetallePostBinding.inflate(inflater, container, false)
        val postId = args.postId
        observarViewModel()

        viewModel.cambiarPost(postId)
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
        }
    }

    private fun setPost(post: Post) {
        binding.textViewTitle.text = post.title
        binding.textViewBody.text = post.body
    }
}
