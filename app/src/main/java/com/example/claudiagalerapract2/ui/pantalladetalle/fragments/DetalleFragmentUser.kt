package com.example.claudiagalerapract2.ui.pantalladetalle.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.claudiagalerapract2.databinding.FragmentDetalleUserBinding
import com.example.claudiagalerapract2.ui.pantalladetalle.state.DetalleUserState
import com.example.claudiagalerapract2.ui.pantalladetalle.viewmodel.DetalleUserViewModel

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetalleFragmentUser : Fragment() {
    //F

    private val viewModel: DetalleUserViewModel by viewModels()
    private var _binding: FragmentDetalleUserBinding? = null
    private val binding get() = _binding!!
    private val args: DetalleFragmentAlbumArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleUserBinding.inflate(inflater, container, false)
        val userId = args.albumId

        observarViewModel()

        return binding.root
    }

    private fun observarViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            state.mensaje?.let { error ->
                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
                viewModel.errorMostrado()
            }
            state.user?.let {
                setUser(state)
            }
        }
    }


    private fun setUser(state: DetalleUserState) {
        state.user?.let { user ->
            binding.textViewName.text = user.name
            binding.textViewUsername.text = user.username
            binding.textViewEmail.text = user.email
            binding.textViewPhone.text = user.phone
            binding.textViewAddress.text = user.address.toString()
        }
    }

    private fun removeAccents(input: String): String {
        return input.map { char ->
            when (char) {
                'á', 'à', 'ä', 'â', 'ã', 'å', 'ā' -> 'a'
                'é', 'è', 'ë', 'ê', 'ē' -> 'e'
                'í', 'ì', 'ï', 'î', 'ī' -> 'i'
                'ó', 'ò', 'ö', 'ô', 'õ', 'ø', 'ō' -> 'o'
                'ú', 'ù', 'ü', 'û', 'ū' -> 'u'
                'ç' -> 'c'
                'ñ' -> 'n'
                'ß' -> 's'
                else -> char
            }
        }.joinToString("")
    }
}