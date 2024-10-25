package com.example.claudiagalerapract2.ui.pantallamain

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.claudiagalerapract2.R
import com.example.claudiagalerapract2.databinding.ActivityMainBinding
import com.example.claudiagalerapract2.domain.modelo.Pelicula
import com.example.claudiagalerapract2.domain.usecases.peliculas.GetPeliculas
import com.example.claudiagalerapract2.ui.pantalladetalle.DetalleActivity


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PeliculaAdapter


    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            GetPeliculas(),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }

        events()
        configureRecyclerView()
        observarState()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getPeliculas()
    }

    private fun observarState() {
        viewModel.uiState.observe(this@MainActivity) { state ->
            adapter.submitList(state.peliculas)
            }
    }

    private fun configureRecyclerView() {

        adapter = PeliculaAdapter(itemClick = { pelicula ->
            navigateToDetail(pelicula.id)

        },
            actions = object : PeliculaAdapter.PeliculasActions {
                override fun onItemClick(pelicula: Pelicula) {
                    navigateToDetail(pelicula.id)
                }

            })

        binding.listaPeliculas.layoutManager = LinearLayoutManager(this)

        binding.listaPeliculas.adapter = adapter

    }


    private fun navigateToDetail(id: Int) {
        val intent = Intent(this, DetalleActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)

    }

    private fun events() {

        binding.fab.setOnClickListener {
            val intent = Intent(this, DetalleActivity::class.java)
            intent.putExtra("nuevo", true)
            intent.putExtra("id", adapter.currentList.last().id+1)
            startActivity(intent)

        }
    }
}