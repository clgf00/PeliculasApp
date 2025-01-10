package com.example.claudiagalerapract2.ui.pantallamain

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.claudiagalerapract2.R
import com.example.claudiagalerapract2.databinding.ActivityMainBinding
import com.example.claudiagalerapract2.ui.album.listado.ListadoAlbumEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MenuProvider {
    private val binding: ActivityMainBinding by lazy {

        ActivityMainBinding.inflate(layoutInflater)
    }
    private var username by Delegates.notNull<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        with(binding) {
            setContentView(root)
            ViewCompat.setOnApplyWindowInsetsListener(main) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
                insets
            }

            val intent = intent

            username = intent.getIntExtra("username", 1).toString()
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController

            bottomAppBar.setupWithNavController(navController)

            setSupportActionBar(topAppBar)

            setupActionBarWithNavController(navController)

            topAppBar.setNavigationOnClickListener {
                navController.navigateUp()
            }
        }
    }
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_appbar_search, menu)


    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.boton -> {
                true
            }
            else-> false
        }
    }
}