package com.example.claudiagalerapract2.ui.photo.listado

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.claudiagalerapract2.databinding.FragmentListadoBinding
import com.example.claudiagalerapract2.domain.modelo.Photo
import com.example.claudiagalerapract2.ui.album.detalle.DetalleFragmentAlbumDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListadoPhotoFragment : Fragment() {

    private val viewModel: ListadoPhotoViewModel by viewModels()
    private var _binding: FragmentListadoBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PhotoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListadoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.handleEvent(ListadoPhotoEvent.GetPhotos)

        configureRecyclerView()
        observarState()
    }

    private fun observarState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    adapter.submitList(state.photos)
                    binding.lista.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun configureRecyclerView() {
        adapter = PhotoAdapter(
            actions = object : PhotoAdapter.PhotoActions {
                override fun onItemClick(photo: Photo) {
                    val photoId = photo.id
                    val action =
                        DetalleFragmentAlbumDirections.actionDetalleFragmentAlbumToDetalleFragmentPhoto(photoId)
                    findNavController().navigate(action)
                }
            })
        binding.lista.apply {
            binding.lista.layoutManager = LinearLayoutManager(activity)
            adapter = this@ListadoPhotoFragment.adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
