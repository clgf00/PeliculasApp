package com.example.claudiagalerapract2.ui.listado

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.claudiagalerapract2.R
import com.example.claudiagalerapract2.databinding.FragmentDetalleBinding
import com.example.claudiagalerapract2.domain.modelo.Pelicula
import com.example.claudiagalerapract2.ui.common.Constantes
import com.example.claudiagalerapract2.ui.pantalladetalle.DetalleState
import com.example.claudiagalerapract2.ui.pantalladetalle.DetalleViewModel

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetalleFragment : Fragment() {

    private val viewModel: DetalleViewModel by viewModels()
    private var id: Int = 0
    private var _binding: FragmentDetalleBinding? = null
    private val binding get() = _binding!!
    private val args: DetalleFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleBinding.inflate(inflater, container, false)
        val id = args.peliculaId
        val nuevo = args.nuevo
        if (nuevo) {
            binding.buttonAdd.visibility = View.VISIBLE
            binding.buttonUpdate.visibility = View.GONE
            binding.buttonDelete.visibility = View.GONE
        } else {
            binding.buttonAdd.visibility = View.GONE
            binding.buttonUpdate.visibility = View.VISIBLE
            binding.buttonDelete.visibility = View.VISIBLE
            viewModel.cambiarPelicula(id)
        }
        setEvents()
        observarViewModel()
        if (!nuevo) {
            viewModel.cambiarPelicula(id)
        }
        return binding.root

    }
    private fun observarViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            state.mensaje?.let { error ->
                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
                viewModel.errorMostrado()
            }
            state.mensaje?.let {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
                viewModel.anyadidoMostrado()
            }
            state.mensaje?.let {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
                viewModel.eliminadoMostrado()
            }
            state.mensaje?.let {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
                viewModel.updateMostrado()
            }

            if (state.mensaje == null) setPelicula(state)
        }
    }

    private fun setEvents() {
        with(binding) {
            fun createPelicula(): Pelicula {
                return Pelicula(
                    id = id,
                    titulo = editTextTitulo.text.toString(),
                    anyoEstreno = editTextAnyoEstreno.text.toString().toIntOrNull() ?: 0,
                    director = editTextDirector.text.toString(),
                    genero = when (radioGroupGenero.checkedRadioButtonId) {
                        R.id.radioAction -> "accion"
                        R.id.radioDrama -> "drama"
                        R.id.radioComedia -> "comedia"
                        R.id.radioTerror -> "terror"
                        R.id.radioFantasia -> "fantasia"
                        else -> ""
                    },
                    recomendado = checkBoxRecomendado.isChecked,
                    calificacion = seekBarCalificacion.value.toDouble()
                )
            }
            buttonAdd.setOnClickListener {
                viewModel.addPelicula(createPelicula())
                findNavController().navigateUp()
            }

            buttonDelete.setOnClickListener {
                viewModel.deletePelicula()
                findNavController().navigateUp()
            }

            buttonUpdate.setOnClickListener {
                viewModel.updatePelicula(createPelicula())
                findNavController().navigateUp()
            }
        }
    }


    private fun setPelicula(state: DetalleState) {
        state.pelicula.let { pelicula ->
            binding.editTextTitulo.setText(state.pelicula.titulo)
            binding.editTextAnyoEstreno.setText(state.pelicula.anyoEstreno.toString())
            binding.editTextDirector.setText(state.pelicula.director)
            binding.checkBoxRecomendado.isChecked = state.pelicula.recomendado
            binding.seekBarCalificacion.value = pelicula.calificacion.toFloat()
            when ((pelicula.genero?.let { removeAccents(it.lowercase()) })) {
                Constantes.ACCION -> binding.radioGroupGenero.check(R.id.radioAction)
                Constantes.DRAMA -> binding.radioGroupGenero.check(R.id.radioDrama)
                Constantes.COMEDIA -> binding.radioGroupGenero.check(R.id.radioComedia)
                Constantes.TERROR -> binding.radioGroupGenero.check(R.id.radioTerror)
                Constantes.FANTASIA -> binding.radioGroupGenero.check(R.id.radioFantasia)
                else -> {
                    binding.radioGroupGenero.clearCheck()
                }
            }
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