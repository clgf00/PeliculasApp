package com.example.claudiagalerapract2.ui.pantallalogin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.claudiagalerapract2.databinding.ActivityLoginBinding
import com.example.claudiagalerapract2.ui.common.Constantes
import com.example.claudiagalerapract2.ui.pantallamain.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModels()

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            setContentView(root)
            ViewCompat.setOnApplyWindowInsetsListener(mainLogin) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
                insets
            }
        }

        binding.loginButton.setOnClickListener {
            val username = binding.userIdEditText.text.toString()
            if (username.isNotEmpty()) {
                loginUser(username.toInt())
            } else {
                Toast.makeText(this, Constantes.INTRODUCE_USUARIO, Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    private fun navigateToMain(id: Int) {
        val intent = Intent(this, MainActivity::class.java)

        // Puedes agregar datos extra si es necesario
        intent.putExtra("userId", id)

        // Inicia la actividad
        startActivity(intent)
    }

    private fun loginUser(id: Int) {
        setLoadingState(true)
        viewModel.login(id).observe(this) { user ->
            setLoadingState(false)
            if (user != null) {
                navigateToMain(id)
            } else {
                Toast.makeText(this, Constantes.NO_ENCONTRADO, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        binding.loadingSpinner.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.loginButton.isEnabled = !isLoading
    }


}