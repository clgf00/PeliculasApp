package com.example.claudiagalerapract2.ui.listado

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.claudiagalerapract2.databinding.FragmentDetalleBinding
import com.example.claudiagalerapract2.databinding.FragmentListadoPeliculasBinding
import com.example.claudiagalerapract2.domain.modelo.Pelicula
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetalleFragment : Fragment() {

    private val viewModel: ListadoViewModel by viewModels()
    private var _binding: FragmentDetalleBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PeliculaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        events()
        observarState()
    }

    private fun observarState() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.peliculas)
        }
    }


    private fun events() {
            viewModel.handleEvent(ListadoEvent.GetPeliculas)

    }
}