package com.example.claudiagalerapract2.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.claudiagalerapract2.ui.navigation.NavigationGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //añadir por aqui el splashScreen. Cambiar el icono en el android manifest.
        //Crear en values un nuevo splash_screen.xml
        setContent {
            val navController = rememberNavController()
            NavigationGraph(navController = navController)
        }
    }
}
