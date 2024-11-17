package com.example.claudiagalerapract2.ui.listado

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.claudiagalerapract2.R
import com.example.claudiagalerapract2.databinding.FragmentListadoBinding
import com.example.claudiagalerapract2.domain.modelo.Hero
import dagger.hilt.android.AndroidEntryPoint
//F
@AndroidEntryPoint
class ListadoFragment : Fragment() {

    private val viewModel: ListadoViewModel by viewModels()
    private var _binding: FragmentListadoBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: HeroAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListadoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.handleEvent(ListadoEvent.GetHeroes)


        configureRecyclerView()
        observarState()

    }

    private fun observarState() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.heroes)

            state.heroes.forEachIndexed { index, hero ->
                val viewHolder = binding.listaHeroes.findViewHolderForAdapterPosition(index)
                val itemView = viewHolder?.itemView
                val imageView = itemView?.findViewById<ImageView>(R.id.hero_image)

                imageView?.load(hero.portrait) {
                    crossfade(true)
                }
            }

            binding.listaHeroes.visibility = View.VISIBLE
        }
    }

    private fun configureRecyclerView() {
        adapter = HeroAdapter(
            actions = object : HeroAdapter.HeroesActions {
                override fun onItemClick(hero: Hero) {
                    val heroId = hero.key
                    val action = ListadoFragmentDirections.actionListadoFragmentToDetalleFragment(heroId, false)
                    findNavController().navigate(action)
                }
            })

        binding.listaHeroes.layoutManager = LinearLayoutManager(activity)
        binding.listaHeroes.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
