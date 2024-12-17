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
                Snackbar.make(binding.root, Constantes.INTRODUCE_USUARIO, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToMain(id: Int) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("userId", id)
        startActivity(intent)
    }

    private fun loginUser(id: Int) {
        setLoadingState(true)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.login(id).collect { user ->
                    setLoadingState(false)
                    if (user != null) {
                        navigateToMain(id)
                    } else {
                        Snackbar.make(binding.root, Constantes.NO_ENCONTRADO, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        binding.loadingSpinner.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.loginButton.isEnabled = !isLoading
    }
}