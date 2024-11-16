package com.example.claudiagalerapract2.ui.pantalladetalle
//F
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.load
import com.example.claudiagalerapract2.R
import com.example.claudiagalerapract2.databinding.ActivityDetalleBinding
import com.example.claudiagalerapract2.domain.modelo.Hero
import com.example.claudiagalerapract2.ui.common.Constantes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetalleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetalleBinding
    private var key: Int = 0

    private val viewModel: DetalleViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detalleFragment)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivityDetalleBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }

        key = intent.getIntExtra(Constantes.KEY, 0)
        val nuevo = intent.getBooleanExtra("nuevo", false)
        observarViewModel()
        if (!nuevo) {
            viewModel.cambiarHeroe(key.toString())
        }
    }

    private fun setHeroImage(hero: Hero) {
        binding.imageHero.load(hero.portrait)  {
            crossfade(true)
        }
    }

    private fun observarViewModel() {
        viewModel.uiState.observe(this@DetalleActivity) { state ->
            state.mensaje?.let { error ->
                Toast.makeText(this@DetalleActivity, error, Toast.LENGTH_SHORT).show()
                viewModel.errorMostrado()
            }
            if (state.hero != null) {
                setHeroImage(state.hero)
            }

            if (state.mensaje == null) setHero(state)
        }
    }


    private fun setHero(state: DetalleState) {
        state.hero.let { hero ->
            binding.editTextName.setText(state.hero?.name)
            binding.textViewDescription.setText(state.hero?.description)
            when ((hero?.role?.let { removeAccents(it.lowercase()) })) {
                Constantes.TANK -> binding.radioGroupRole.check(R.id.radioTank)
                Constantes.DAMAGE -> binding.radioGroupRole.check(R.id.radioDamage)
                Constantes.SUPPORT -> binding.radioGroupRole.check(R.id.radioSupport)

                else -> {
                    binding.radioGroupRole.clearCheck()
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
