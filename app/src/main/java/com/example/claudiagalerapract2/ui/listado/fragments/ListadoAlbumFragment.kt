package com.example.claudiagalerapract2.ui.listado.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.claudiagalerapract2.databinding.FragmentListadoBinding
import com.example.claudiagalerapract2.domain.modelo.Album
import com.example.claudiagalerapract2.ui.listado.adapters.AlbumAdapter
import com.example.claudiagalerapract2.ui.listado.events.ListadoAlbumEvent
import com.example.claudiagalerapract2.ui.listado.viewmodel.ListadoAlbumViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListadoAlbumFragment : Fragment() {

    private val viewModel: ListadoAlbumViewModel by viewModels()
    private var _binding: FragmentListadoBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: AlbumAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListadoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.handleEvent(ListadoAlbumEvent.GetAlbums)

        configureRecyclerView()
        observarState()
    }

    private fun observarState() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.albums)
            binding.lista?.visibility = View.VISIBLE
        }
    }

    private fun configureRecyclerView() {
        adapter = AlbumAdapter(
            actions = object : AlbumAdapter.AlbumActions {
                override fun onItemClick(album: Album) {
                    val albumId = album.id
                    val action = ListadoAlbumFragmentDirections.actionListadoAlbumFragmentToDetalleFragmentAlbum(albumId)
                    findNavController().navigate(action)
                }
            })

        binding.lista?.layoutManager = LinearLayoutManager(activity)
        binding.lista?.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
