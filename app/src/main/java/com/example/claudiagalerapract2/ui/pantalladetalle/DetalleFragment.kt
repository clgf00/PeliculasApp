package com.example.claudiagalerapract2.ui.pantalladetalle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.claudiagalerapract2.R
import com.example.claudiagalerapract2.databinding.FragmentDetalleBinding
import com.example.claudiagalerapract2.ui.common.Constantes

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetalleFragment : Fragment() {
    //F

    private val viewModel: DetalleViewModel by viewModels()
    private var _binding: FragmentDetalleBinding? = null
    private val binding get() = _binding!!
    private val args: DetalleFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleBinding.inflate(inflater, container, false)
        val heroId = args.heroId

        val nuevo = args.nuevo
        observarViewModel()
        if (!nuevo) {
            viewModel.cambiarHeroe(heroId)
        }
        return binding.root
    }

    private fun observarViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            state.mensaje?.let { error ->
                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
                viewModel.errorMostrado()
            }
            state.hero?.let {
                setHero(state)
            }
        }
    }


    private fun setHero(state: DetalleState) {
        //llamarlo en observar de uiState
        state.hero?.let { hero ->
            val imageUrl = hero.portrait
            binding.imageHero.load(imageUrl) {
                crossfade(true)
            }
            binding.editTextName.setText(hero.name)
            binding.textViewDescription.text = hero.description
            binding.editTextAge.setText(hero.age)
            when (removeAccents(hero.role.lowercase())) {
                Constantes.TANK -> binding.radioGroupRole.check(R.id.radioTank)
                Constantes.DAMAGE -> binding.radioGroupRole.check(R.id.radioDamage)
                Constantes.SUPPORT -> binding.radioGroupRole.check(R.id.radioSupport)
                else -> binding.radioGroupRole.clearCheck()
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