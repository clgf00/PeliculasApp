package com.example.claudiagalerapract2.ui.pantalladetalle

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.claudiagalerapract2.R
import com.example.claudiagalerapract2.databinding.ActivityDetalleBinding
import com.example.claudiagalerapract2.domain.modelo.Pelicula
import com.example.claudiagalerapract2.domain.usecases.peliculas.AddPeliculaUseCase
import com.example.claudiagalerapract2.domain.usecases.peliculas.DeletePeliculaUseCase
import com.example.claudiagalerapract2.domain.usecases.peliculas.GetPeliculas
import com.example.claudiagalerapract2.domain.usecases.peliculas.UpdatePeliculaUseCase
import com.example.claudiagalerapract2.ui.common.Constantes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetalleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetalleBinding
    private var id: Int = 0

    private val viewModel: DetalleViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detalle)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityDetalleBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }

        id = intent.getIntExtra(Constantes.ID, 0)
        val nuevo = intent.getBooleanExtra("nuevo", false)
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
    }

    private fun observarViewModel() {
        viewModel.uiState.observe(this@DetalleActivity) { state ->
            state.mensaje?.let { error ->
                Toast.makeText(this@DetalleActivity, error, Toast.LENGTH_SHORT).show()
                viewModel.errorMostrado()
            }
            state.mensaje?.let {
                Toast.makeText(this@DetalleActivity, it, Toast.LENGTH_SHORT).show()
                viewModel.anyadidoMostrado()
            }
            state.mensaje?.let {
                Toast.makeText(this@DetalleActivity, it, Toast.LENGTH_SHORT).show()
                viewModel.eliminadoMostrado()
            }
            state.mensaje?.let {
                Toast.makeText(this@DetalleActivity, it, Toast.LENGTH_SHORT).show()
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
                    genero = findViewById<RadioButton>(radioGroupGenero.checkedRadioButtonId).text.toString()
                        .lowercase(),
                    recomendado = checkBoxRecomendado.isChecked,
                    calificacion = seekBarCalificacion.value.toDouble()
                )
            }
            buttonAdd.setOnClickListener {
                viewModel.addPelicula(createPelicula())
                finish()
            }

            buttonDelete.setOnClickListener {
                viewModel.deletePelicula()
                finish()
            }

            buttonUpdate.setOnClickListener {
                viewModel.updatePelicula(createPelicula())
                finish()
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
