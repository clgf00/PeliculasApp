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
import com.example.claudiagalerapract2.databinding.FragmentDetalleAlbumBinding
import com.example.claudiagalerapract2.domain.modelo.Photo
import com.example.claudiagalerapract2.ui.listado.adapters.PhotoAdapter
import com.example.claudiagalerapract2.ui.pantalladetalle.viewmodel.DetalleAlbumViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetalleFragmentAlbum : Fragment() {
    //F

    private val viewModel: DetalleAlbumViewModel by viewModels()
    private var _binding: FragmentDetalleAlbumBinding? = null
    private val binding get() = _binding!!
    private val args: DetalleFragmentAlbumArgs by navArgs()
    private lateinit var adapter: PhotoAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleAlbumBinding.inflate(inflater, container, false)
        configureRecyclerView()
        viewModel.cambiarAlbum(args.albumId)
        observarViewModel()

        binding.deleteAlbumButton.setOnClickListener {
            viewModel.eliminarAlbum(args.albumId)
            findNavController().navigateUp()
        }

        return binding.root

    }

    private fun configureRecyclerView() {
        adapter = PhotoAdapter(object : PhotoAdapter.PhotoActions {
            override fun onItemClick(photo: Photo) {
                val action = DetalleFragmentAlbumDirections
                    .actionDetalleFragmentAlbumToDetalleFragmentPhoto(photo.id)
                findNavController().navigate(action)
            }
        })

        binding.photoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.photoRecyclerView.adapter = adapter
    }

    private fun observarViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            state.album?.let { album ->
                binding.albumTitleEditText.setText(album.title)
            }
            adapter.submitList(state.photos)
            state.mensaje?.let { mensaje ->
                Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
                viewModel.errorMostrado()
            }

        }
    }
}

