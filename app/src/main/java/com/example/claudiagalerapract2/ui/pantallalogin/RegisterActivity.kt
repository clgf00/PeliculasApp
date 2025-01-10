package com.example.claudiagalerapract2.ui.pantallalogin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.claudiagalerapract2.R
import com.example.claudiagalerapract2.data.local.UserDao
import com.example.claudiagalerapract2.data.local.modelo.UserEntity
import com.example.claudiagalerapract2.ui.common.Constantes
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        registerButton = findViewById(R.id.registerButton)

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch {
                    val existingUser = userDao.getUserByUsername(username)
                    if (existingUser != null) {
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            Constantes.ERROR,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    } else {
                        val newUser = UserEntity(id = username.hashCode(), username = username, password = password)
                        userDao.insert(newUser)
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            Constantes.EXITO,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    Constantes.INTRODUCE_DATOS_CORRECTOS,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }
}
