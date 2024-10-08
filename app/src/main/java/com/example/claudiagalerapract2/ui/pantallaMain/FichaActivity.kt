package com.example.claudiagalerapract2.ui.pantallaMain

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.claudiagalerapract2.R
import com.example.claudiagalerapract2.data.Repository
import com.example.claudiagalerapract2.databinding.ActivityMainBinding
import com.example.claudiagalerapract2.domain.modelo.Pelicula
import com.example.claudiagalerapract2.domain.usecases.peliculas.AddPeliculaUseCase
import com.example.claudiagalerapract2.domain.usecases.peliculas.GetPeliculas
import com.example.claudiagalerapract2.utils.StringProvider

class FichaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: FichaViewModel by viewModels {
        MainViewModelFactory(
            StringProvider.instance(this),
            AddPeliculaUseCase(),
            GetPeliculas(Repository()),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }

        setEvents();
        observarViewModel()

    }

    private fun observarViewModel() {
        viewModel.uiState.observe(this@FichaActivity) { state ->

            state.error?.let { error ->
                Toast.makeText(this@FichaActivity, error, Toast.LENGTH_SHORT).show()
                viewModel.errorMostrado()
            }

            if (state.error == null)
                state.pelicula.let { pelicula ->
                    binding.editTextTitulo.setText(state.pelicula.titulo)
                    binding.editTextAnyoEstreno.setText(state.pelicula.anyoEstreno.toString())
                    binding.editTextDirector.setText(state.pelicula.director)
                    binding.checkBoxRecomendado.isChecked = true
                    binding.seekBarCalificacion.progress = state.seekBarValue
                    when (pelicula.genero) {
                        "accion" -> binding.radioGroupGenero.check(R.id.radioAction)
                        "drama" -> binding.radioGroupGenero.check(R.id.radioDrama)
                        "comedia" -> binding.radioGroupGenero.check(R.id.radioComedia)
                        "terror" -> binding.radioGroupGenero.check(R.id.radioTerror)
                        "fantasia" -> binding.radioGroupGenero.check(R.id.radioFantasia)

                        else -> {
                            binding.radioGroupGenero.clearCheck()
                        }
                    }
                }
            binding.buttonSiguiente.isEnabled = state.habilitarSiguiente
        }
    }

    private fun setEvents() {
        with(binding) {
            buttonSiguiente.setOnClickListener {
                val seekBarValue = seekBarCalificacion.progress
                val pelicula = Pelicula(
                    titulo = editTextTitulo.text.toString(),
                    director = editTextDirector.text.toString(),
                    anyoEstreno = editTextAnyoEstreno.text.toString().toIntOrNull(),
                    genero = radioGroupGenero.checkedRadioButtonId.toString(),
                    recomendado = checkBoxRecomendado.isChecked,
                    calificacion = seekBarValue
                )
                viewModel.addPelicula(pelicula)
                //TODO hacer la logica para que al pulsar el boton muestre las peliculas de la lista
                // Hacer la logica en el FichaViewModel, no en el Activity
            }
            viewModel.siguiente()
        }
    }
}