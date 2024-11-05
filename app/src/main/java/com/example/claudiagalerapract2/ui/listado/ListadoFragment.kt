package com.example.claudiagalerapract2.ui.listado

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.claudiagalerapract2.databinding.FragmentListadoPeliculasBinding
import com.example.claudiagalerapract2.domain.modelo.Pelicula
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListadoFragment : Fragment() {

    private val viewModel: ListadoViewModel by viewModels()
    private var _binding: FragmentListadoPeliculasBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PeliculaAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListadoPeliculasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.handleEvent(ListadoEvent.GetPeliculas)
        binding.fab.setOnClickListener {
        }

        configureRecyclerView()
        observarState()

    }

    private fun observarState() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.peliculas)
            binding.listaPeliculas.visibility = View.VISIBLE

        }
    }

    private fun configureRecyclerView() {

        adapter = PeliculaAdapter(
            actions = object : PeliculaAdapter.PeliculasActions {
                override fun onItemClick(pelicula: Pelicula) {
                }
            })

        binding.listaPeliculas.layoutManager = LinearLayoutManager(activity)

        binding.listaPeliculas.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
