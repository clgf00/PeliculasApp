package com.example.claudiagalerapract2.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.claudiagalerapract2.ui.albumpantalla.AlbumViewModel
import com.example.claudiagalerapract2.ui.todopantalla.TodoViewModel
import com.example.claudiagalerapract2.ui.albumpantalla.AlbumsScreen
import com.example.claudiagalerapract2.ui.common.Constantes
import com.example.claudiagalerapract2.ui.photopantalla.PhotosScreen
import com.example.claudiagalerapract2.ui.screens.TodosScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationGraph(navController: NavHostController) {
    // ViewModels obtenidos mediante hilt
    val todoViewModel: TodoViewModel = hiltViewModel()
    val albumViewModel: AlbumViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(Constantes.ALBUMTODOS) })
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = TodosDestination.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            // Pantalla de "Todos"
            composable(TodosDestination.route) {
                TodosScreen(viewModel = todoViewModel, paddingValues = paddingValues)
            }
            // Pantalla de "Álbumes"
            composable(AlbumsDestination.route) {
                AlbumsScreen(
                    viewModel = albumViewModel,
                    paddingValues = paddingValues,
                    // Al pulsar un álbum, navegamos a la pantalla de fotos pasando el id del álbum
                    onAlbumClick = { albumId ->
                        navController.navigate(PhotosDestination.createRoute(albumId))
                    }
                )
            }
            composable(
                route = "${PhotosDestination.route}/{${PhotosDestination.argAlbumId}}",
                arguments = listOf(navArgument(PhotosDestination.argAlbumId) {
                    type = NavType.IntType
                })
            ) { backStackEntry ->
                // Se extrae el albumId pasado como argumento; si no existe, se usa -1 (o manejar error)
                val albumId = backStackEntry.arguments?.getInt(PhotosDestination.argAlbumId) ?: -1
                // Se muestra la pantalla de fotos (PhotoViewModel se obtiene vía Hilt en el composable)
                PhotosScreen(paddingValues = paddingValues, albumId = albumId)
            }
        }
    }
}
