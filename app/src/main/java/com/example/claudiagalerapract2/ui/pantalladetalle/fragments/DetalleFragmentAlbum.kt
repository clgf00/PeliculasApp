package com.example.claudiagalerapract2.ui.pantalladetalle.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.claudiagalerapract2.databinding.FragmentDetalleAlbumBinding
import com.example.claudiagalerapract2.ui.pantalladetalle.state.DetalleAlbumState
import com.example.claudiagalerapract2.ui.pantalladetalle.viewmodel.DetalleAlbumViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetalleFragmentAlbum : Fragment() {
    //F

    private val viewModel: DetalleAlbumViewModel by viewModels()
    private var _binding: FragmentDetalleAlbumBinding? = null
    private val binding get() = _binding!!
    private val args: DetalleFragmentAlbumArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleAlbumBinding.inflate(inflater, container, false)
        observarViewModel()

        return binding.root
    }

    private fun observarViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            state.mensaje?.let { error ->
                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
                viewModel.errorMostrado()
            }
            state.album?.let {
                setAlbum(state)
            }
        }
    }


    private fun setAlbum(state: DetalleAlbumState) {
        state.album?.let { album ->
            binding.albumTitleTextView.text = album.title
            }
        }
    }

