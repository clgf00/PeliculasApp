package com.example.claudiagalerapract2.ui.album.detalle

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
import com.example.claudiagalerapract2.databinding.FragmentDetalleAlbumBinding
import com.example.claudiagalerapract2.domain.modelo.Photo
import com.example.claudiagalerapract2.ui.pantallamain.MainActivity
import com.example.claudiagalerapract2.ui.photo.listado.ListadoPhotoEvent
import com.example.claudiagalerapract2.ui.photo.listado.PhotoAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_appbar_search, menu)

                val actionSearch = menu.findItem(R.id.search).actionView as SearchView
                actionSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean = false

                    override fun onQueryTextChange(newText: String?): Boolean {
                        viewModel.handleEvent(DetalleAlbumEvent.FilterPhotos(newText.orEmpty()))
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    state.album?.let { album ->
                        binding.albumTitleEditText.setText(album.title)
                    }
                    adapter.submitList(state.photos)
                    state.mensaje?.let { mensaje ->
                        Snackbar.make(requireView(), mensaje, Snackbar.LENGTH_SHORT).show()
                        viewModel.errorMostrado()
                    }
                }
            }
        }
    }
}

