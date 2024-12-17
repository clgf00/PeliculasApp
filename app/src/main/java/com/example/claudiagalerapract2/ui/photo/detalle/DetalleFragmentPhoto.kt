package com.example.claudiagalerapract2.ui.photo.detalle

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
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.claudiagalerapract2.databinding.FragmentDetallePhotoBinding
import com.example.claudiagalerapract2.ui.common.Constantes
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetalleFragmentPhoto : Fragment() {

    private val viewModel: DetallePhotoViewModel by viewModels()

    private var _binding: FragmentDetallePhotoBinding? = null
    private val binding get() = _binding!!

    private val args: DetalleFragmentPhotoArgs by navArgs()

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            _binding = FragmentDetallePhotoBinding.inflate(inflater, container, false)
            val photoId = args.photoId
            observarViewModel()
            viewModel.cambiarPhoto(photoId)

            binding.deletePhotoButton.setOnClickListener {
                viewModel.eliminarPhoto(photoId)
                Snackbar.make(requireView(), Constantes.ELIMINADO, Snackbar.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }

            return binding.root
        }

    private fun observarViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    state.mensaje?.let { error ->
                        Snackbar.make(requireView(), error, Snackbar.LENGTH_SHORT).show()
                        viewModel.errorMostrado()
                    }

                    state.photo?.let {
                        setPhoto(state)
                    }
                }
            }
        }
    }

    private fun setPhoto(state: DetallePhotoState) {
        state.photo?.let { photo ->
            binding.photoTitleEditText.setText(photo.title)
            binding.photoImageView.load(photo.url) {
                crossfade(true)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
