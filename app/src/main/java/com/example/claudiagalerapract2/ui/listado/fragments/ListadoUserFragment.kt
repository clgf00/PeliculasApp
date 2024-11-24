package com.example.claudiagalerapract2.ui.listado.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.claudiagalerapract2.R
import com.example.claudiagalerapract2.databinding.FragmentListadoBinding
import com.example.claudiagalerapract2.domain.modelo.User
import com.example.claudiagalerapract2.ui.listado.adapters.UserAdapter
import com.example.claudiagalerapract2.ui.listado.events.ListadoUserEvent
import com.example.claudiagalerapract2.ui.listado.viewmodel.ListadoUserViewModel
import dagger.hilt.android.AndroidEntryPoint

//F
@AndroidEntryPoint
class ListadoUserFragment : Fragment() {

    private val viewModel: ListadoUserViewModel by viewModels()
    private var _binding: FragmentListadoBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: UserAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListadoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.handleEvent(ListadoUserEvent.GetUsers)


        configureRecyclerView()
        observarState()

    }

    private fun observarState() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.users)

            state.users.forEachIndexed { index, user ->
                val viewHolder = binding.lista?.findViewHolderForAdapterPosition(index)
                val itemView = viewHolder?.itemView
                val imageView = itemView?.findViewById<ImageView>(R.id.image)
                imageView?.load(user.username) {
                    crossfade(true)
                }
            }

            binding.listaUsers?.visibility = View.VISIBLE
        }
    }

    private fun configureRecyclerView() {
        adapter = UserAdapter(
            actions = object : UserAdapter.UserActions {
                override fun onItemClick(user: User) {
                    val userId = user.id
                //    val action = ListadoUserFragmentDirections.actionListadoFragmentToFragmentListadoPost(userId)
                //    findNavController().navigate(action)
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
