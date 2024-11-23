package com.example.claudiagalerapract2.ui.pantalladetalle.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.claudiagalerapract2.databinding.FragmentDetallePhotoBinding
import com.example.claudiagalerapract2.ui.pantalladetalle.state.DetallePhotoState
import com.example.claudiagalerapract2.ui.pantalladetalle.viewmodel.DetallePhotoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetalleFragmentPhoto : Fragment() {

    private val viewModel: DetallePhotoViewModel by viewModels()

    private var _binding: FragmentDetallePhotoBinding? = null
    private val binding get() = _binding!!

    private val args: DetalleFragmentPhotoArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetallePhotoBinding.inflate(inflater, container, false)

        val photoId = args.photoId

        observarViewModel()

        viewModel.cambiarPhoto(photoId)

        return binding.root
    }

    private fun observarViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            state.mensaje?.let { error ->
                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
                viewModel.errorMostrado()
            }

            state.photo?.let {
                setPhoto(state)
            }
        }
    }

    private fun setPhoto(state: DetallePhotoState) {
        state.photo?.let { photo ->
            binding.photoTitleTextView.text = photo.title
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
