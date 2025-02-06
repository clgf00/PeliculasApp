package com.example.claudiagalerapract2.ui.pantallalogin

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.claudiagalerapract2.ui.MainActivity
import com.example.claudiagalerapract2.ui.common.Constantes
import com.example.claudiagalerapract2.ui.theme.ClaudiaGaleraApiCompose
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ClaudiaGaleraApiCompose {
                LoginScreen(
                    onLoginSuccess = { username ->
                        navigateToMain(username)
                    }
                )
            }
        }
    }

    private fun navigateToMain(username: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(Constantes.USER, username)
        startActivity(intent)
        finish()
    }
}
