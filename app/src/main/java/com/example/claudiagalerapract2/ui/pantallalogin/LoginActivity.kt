package com.example.claudiagalerapract2.ui.pantallalogin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.claudiagalerapract2.databinding.ActivityLoginBinding
import com.example.claudiagalerapract2.domain.modelo.User
import com.example.claudiagalerapract2.ui.common.Constantes
import com.example.claudiagalerapract2.ui.pantallamain.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModels()

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            ViewCompat.setOnApplyWindowInsetsListener(mainLogin) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
                insets
            }
        }

        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                loginUser(username, password)
            } else {
                Snackbar.make(binding.root, Constantes.INTRODUCE_USUARIO, Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.registerButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                registerUser(username, password)
            } else {
                Snackbar.make(binding.root, Constantes.INTRODUCE_USUARIO, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(username: String, password: String) {
        setLoadingState(true)
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.login(username, password)
                viewModel.user.collect { user ->
                    setLoadingState(false)
                    if (user != null) {
                        navigateToMain(username)
                    } else {
                        Snackbar.make(binding.root, Constantes.NO_ENCONTRADO, Snackbar.LENGTH_SHORT).show()                    }
                }
            }
        }
    }

    private fun registerUser(username: String, password: String) {
        setLoadingState(true)
        lifecycleScope.launch {
            val existingUser = viewModel.getUser(username)
            if (existingUser != null) {
                Snackbar.make(binding.root, Constantes.ERROR, Snackbar.LENGTH_SHORT).show()
            } else {
                val newUser = User(username = username, password = password)
                viewModel.register(newUser)
                Snackbar.make(binding.root, Constantes.EXITO, Snackbar.LENGTH_SHORT).show()
            }
            setLoadingState(false)
        }
    }


    private fun navigateToMain(username: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("username", username)
        startActivity(intent)
        finish()
    }


    private fun setLoadingState(isLoading: Boolean) {
        binding.loadingSpinner.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.loginButton.isEnabled = !isLoading
        binding.registerButton.isEnabled = !isLoading
    }
}
